package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.AppClient
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.db.MovieDao
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Error
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.model.entity.ActorEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieActorCrossRefEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieEntity
import com.gmail.volkovskiyda.movieapp.model.response.config.ConfigurationResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieListRepositoryImpl @Inject constructor(
    private val appClient: AppClient,
    private val movieDao: MovieDao,
) : MovieListRepository {

    private lateinit var configuration: ConfigurationResponse
    private lateinit var genre: Map<Int, String>
    private val movieCreditStates = mutableListOf<MovieStatus>()
    private val errors = MutableSharedFlow<Error>()

    override fun getMovieList(): Flow<List<Movie>> = movieDao.movieCast().map { list ->
        list.map { entity ->
            with(entity.movie) {
                Movie(
                    id, title, duration = "", image, imageBackground, genre, storyline,
                    entity.actors.map { actor -> with(actor) { Actor(id, name, image) } },
                    review, reviewCount
                )
            }
        }
    }.onStart {
        initConfig()
        appClient.popular().fold(onSuccess = { listResponse ->
            prepareDetails(listResponse.movies.map { response ->
                MovieEntity(
                    response.id.toString(),
                    response.title,
                    image(response.posterPath),
                    imageBackground(response.backdropPath),
                    response.genreIds.map { id ->
                        genre[id]
                    }.joinToString(limit = 3, truncated = ""),
                    response.overview,
                    (response.voteAverage / 2).roundToInt(),
                    response.voteCount,
                )
            })

            val movieCast = movieCreditStates.associate { state ->
                when (state) {
                    is MovieStatus.Ready -> state.movie to state.cast
                    else -> state.movie to emptyList()
                }
            }

            movieDao.insertMovies(movieCast.keys.toList())
            movieDao.insertActors(movieCast.values.flatten().distinctBy { it.id })
            movieDao.insertMovieActors(movieCast.flatMap { (movie, actors) ->
                actors.map { actor -> MovieActorCrossRefEntity(movie.id, actor.id) }
            })
        }, onFailure = {
            errors.emit(Error.Resource(R.string.internal_error_movie_list))
        })
    }

    override fun observeErrors(): Flow<Error> = errors

    private suspend fun prepareDetails(movies: List<MovieEntity>) {
        movieCreditStates.clear()
        movieCreditStates.addAll(movies.map { MovieStatus.Init(it) })

        requestDetails()
    }

    private suspend fun requestDetails() {
        val initial = movieCreditStates.filterIsInstance<MovieStatus.Init>()
        if (initial.isEmpty()) return

        val indexToInsert = movieCreditStates.size - initial.size

        val processing = initial.take(3)
        val requestMovies = processing.map { it.movie }
        val loading = requestMovies.map { MovieStatus.Loading(it) }
        movieCreditStates.removeAll(processing)
        movieCreditStates.addAll(indexToInsert, loading)
        val credits =
            coroutineScope {
                requestMovies.map { movie ->
                    async { movie to credits(movie.id) }
                }.awaitAll().toMap()
            }


        val ready = loading.map { it.movie }.map { MovieStatus.Ready(it, credits[it].orEmpty()) }
        movieCreditStates.removeAll(loading)
        movieCreditStates.addAll(indexToInsert, ready)

        requestDetails()
    }

    private suspend fun credits(movieId: String): List<ActorEntity> =
        appClient.movieCredits(movieId).getOrNull()?.cast.orEmpty().mapNotNull { response ->
            response.profilePath?.let { image ->
                ActorEntity(response.id.toString(), response.name, profile(image))
            }
        }

    private suspend fun initConfig() {
        if (::configuration.isInitialized.not()) {
            appClient.configuration().fold(onSuccess = { response ->
                configuration = response
            }, onFailure = {
                errors.emit(Error.Resource(R.string.internal_error))
            })
        }
        if (::genre.isInitialized.not()) genre =
            appClient.genreList().getOrNull()?.genres.orEmpty().associate { it.id to it.name }
    }

    private fun image(path: String): String =
        "${configuration.images.secureBaseUrl}${last(configuration.images.posterSizes)}$path"

    private fun imageBackground(path: String): String =
        "${configuration.images.secureBaseUrl}${last(configuration.images.backdropSizes)}$path"

    private fun profile(path: String): String =
        "${configuration.images.secureBaseUrl}${last(configuration.images.profileSizes)}$path"

    private fun last(list: List<String>) = list.dropLast(1).last()

    private sealed class MovieStatus(open val movie: MovieEntity) {
        class Init(movie: MovieEntity) : MovieStatus(movie)
        class Loading(movie: MovieEntity) : MovieStatus(movie)
        data class Ready(
            override val movie: MovieEntity, val cast: List<ActorEntity>
        ) : MovieStatus(movie)
    }
}