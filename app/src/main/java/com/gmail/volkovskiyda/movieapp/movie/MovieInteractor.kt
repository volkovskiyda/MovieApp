package com.gmail.volkovskiyda.movieapp.movie

import com.gmail.volkovskiyda.movieapp.app.AppNavigator
import com.gmail.volkovskiyda.movieapp.list.MovieListRepository
import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val movieRepository: MovieRepository,
    private val appNavigator: AppNavigator
) {

    suspend fun loadMovie(movieId: String): Movie? =
        movieListRepository.getMovieList().firstOrNull()?.find { it.id == movieId }
            ?: movieRepository.fetchMovie(movieId)

    fun popBackStack() = appNavigator.withNavController { popBackStack() }
}