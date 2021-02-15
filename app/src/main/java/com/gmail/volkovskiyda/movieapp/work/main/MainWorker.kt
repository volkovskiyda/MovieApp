package com.gmail.volkovskiyda.movieapp.work.main

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.volkovskiyda.movieapp.app.AppClient
import com.gmail.volkovskiyda.movieapp.app.AppNotification
import com.gmail.volkovskiyda.movieapp.db.MovieDao
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.model.entity.ActorEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieActorCrossRefEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieEntity
import com.gmail.volkovskiyda.movieapp.model.response.config.ConfigurationResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlin.math.roundToInt

@HiltWorker
class MainWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val appClient: AppClient,
    private val movieDao: MovieDao,
    private val notification: AppNotification,
) : CoroutineWorker(context, workerParameters) {

    private lateinit var configuration: ConfigurationResponse
    private lateinit var genre: Map<Int, String>
    private val movieCreditStates = mutableListOf<MovieStatus>()

    override suspend fun doWork(): Result {
        populateData()
        return Result.success()
    }

    private suspend fun populateData() {
        initConfig()
        appClient.popular().fold(onSuccess = { listResponse ->
            prepareDetails(
                listResponse.movies.map { response ->
                    MovieEntity(
                        id = response.id.toString(),
                        title = response.title,
                        duration = "",
                        image = image(response.posterPath),
                        imageBackground = imageBackground(response.backdropPath),
                        genre = response.genreIds.map { id ->
                            genre[id]
                        }.joinToString(limit = 3, truncated = ""),
                        storyline = response.overview,
                        review = (response.voteAverage / 2).roundToInt(),
                        reviewCount = response.voteCount,
                    )
                }
            )

            val movieCast = movieCreditStates.associate { state ->
                when (state) {
                    is MovieStatus.Ready -> state.movie to state.actors
                    else -> state.movie to emptyList()
                }
            }

            movieDao.replace(
                movies = movieCast.keys.toList(),
                actors = movieCast.values.flatten().distinctBy { it.id },
                movieActors = movieCast.flatMap { (movie, actors) ->
                    actors.map { actor -> MovieActorCrossRefEntity(movie.id, actor.id) }
                }
            )
            movieDao.movieCast().first().firstOrNull()?.let { entity ->
                with(entity.movie) {
                    Movie(
                        id = id,
                        title = title,
                        duration = duration,
                        image = image,
                        imageBackground = imageBackground,
                        genre = genre,
                        storyline = storyline,
                        actors = entity.actors.map { entity ->
                            with(entity) { Actor(id, name, image) }
                        },
                        review = review,
                        reviewCount = reviewCount
                    )
                }
            }?.run { notification.showMovieNotification(this) }
        }, onFailure = {})
    }

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
                ActorEntity(
                    response.id.toString(),
                    response.name,
                    profile(image)
                )
            }
        }

    private suspend fun initConfig() {
        if (::configuration.isInitialized.not()) {
            appClient.configuration().fold(onSuccess = { response ->
                configuration = response
            }, onFailure = {})
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
            override val movie: MovieEntity, val actors: List<ActorEntity>
        ) : MovieStatus(movie)
    }
}