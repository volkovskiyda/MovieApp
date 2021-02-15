package com.gmail.volkovskiyda.movieapp.fetch

import com.gmail.volkovskiyda.movieapp.app.AppClient
import com.gmail.volkovskiyda.movieapp.image
import com.gmail.volkovskiyda.movieapp.imageBackground
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.profile
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.math.roundToInt

class FetchMovieRepositoryImpl @Inject constructor(
    private val appClient: AppClient
) : FetchMovieRepository {

    override suspend fun fetch(movieId: String): Movie? = coroutineScope {
        val deferredConfigurationResponse = async { appClient.configuration() }
        val deferredMovieResponse = async { appClient.movie(movieId).getOrNull() }
        val deferredMovieCredits =
            async { appClient.movieCredits(movieId).getOrNull()?.cast.orEmpty() }
        val configurationResponse =
            deferredConfigurationResponse.await().getOrNull() ?: return@coroutineScope null
        val movieResponse = deferredMovieResponse.await() ?: return@coroutineScope null
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
}