package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.AppClient
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Error
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.model.response.config.ConfigurationResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieListRepositoryImpl @Inject constructor(
    private val appClient: AppClient
) : MovieListRepository {

    private lateinit var configuration: ConfigurationResponse
    private lateinit var genre: Map<Int, String>
    private val movieCreditStates = mutableListOf<MovieStatus>()
    private val movies = MutableStateFlow<List<Movie>>(emptyList())
    private val errors = MutableSharedFlow<Error>()

    override fun getMovieList(): Flow<List<Movie>> = movies.onStart {
        initConfig()
        appClient.popular().fold(onSuccess = { listResponse ->
            movies.value = listResponse.movies.map { response ->
                Movie(
                    id = response.id.toString(),
                    title = response.title,
                    duration = "",
                    image = image(response.posterPath),
                    imageBackground = imageBackground(response.backdropPath),
                    genre = response.genreIds.map { id ->
                        genre[id]
                    }.joinToString(limit = 3, truncated = ""),
                    rating = "PG",
                    storyline = response.overview,
                    actors = emptyList(),
                    review = (response.voteAverage / 2).roundToInt(),
                    reviewCount = response.voteCount,
                )
            }
            prepareDetails()
        }, onFailure = {
            errors.emit(Error.Resource(R.string.internal_error_movie_list))
        })
    }

    override fun observeErrors(): Flow<Error> = errors

    private suspend fun prepareDetails() {
        val movies = movies.value
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
        movies.value = movieCreditStates.map { state ->
            when (state) {
                is MovieStatus.Ready -> state.movie.copy(actors = state.cast)
                else -> state.movie
            }
        }

        requestDetails()
    }

    private suspend fun credits(movieId: String): List<Actor> =
        appClient.movieCredits(movieId).getOrNull()?.cast.orEmpty().mapNotNull { response ->
            response.profilePath?.let { image -> Actor(response.name, profile(image)) }
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

    private sealed class MovieStatus(open val movie: Movie) {
        class Init(movie: Movie) : MovieStatus(movie)
        class Loading(movie: Movie) : MovieStatus(movie)
        data class Ready(override val movie: Movie, val cast: List<Actor>) : MovieStatus(movie)
    }
}