package com.gmail.volkovskiyda.movieapp.movie

import com.gmail.volkovskiyda.movieapp.app.AppClient
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.model.response.config.ConfigurationResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieRepositoryImpl @Inject constructor(
    private val appClient: AppClient,
) : MovieRepository {

    override suspend fun fetchMovie(movieId: String): Movie? = coroutineScope {
        val deferredConfigurationResponse = async { appClient.configuration() }
        val deferredMovieResponse = async { appClient.movie(movieId).getOrNull() }
        val deferredMovieCredits =
            async { appClient.movieCredits(movieId).getOrNull()?.cast.orEmpty() }
        val movieResponse = deferredMovieResponse.await() ?: return@coroutineScope null
        val configurationResponse =
            deferredConfigurationResponse.await().getOrNull() ?: return@coroutineScope null
        Movie(
            id = movieResponse.id.toString(),
            title = movieResponse.title,
            duration = "",
            image = configurationResponse.image(movieResponse.posterPath),
            imageBackground = configurationResponse.imageBackground(movieResponse.backdropPath),
            genre = movieResponse.genresResponse.joinToString(limit = 3, truncated = "") { genre ->
                genre.name
            },
            storyline = movieResponse.overview,
            actors = deferredMovieCredits.await().mapNotNull { response ->
                response.profilePath?.let { image ->
                    Actor(
                        response.id.toString(),
                        response.name,
                        configurationResponse.profile(image)
                    )
                }
            },
            review = (movieResponse.voteAverage / 2).roundToInt(),
            reviewCount = movieResponse.voteCount,
        )
    }

    private fun ConfigurationResponse.image(path: String): String =
        "${images.secureBaseUrl}${last(images.posterSizes)}$path"

    private fun ConfigurationResponse.imageBackground(path: String): String =
        "${images.secureBaseUrl}${last(images.backdropSizes)}$path"

    private fun ConfigurationResponse.profile(path: String): String =
        "${images.secureBaseUrl}${last(images.profileSizes)}$path"

    private fun last(list: List<String>) = list.dropLast(1).last()
}