package com.gmail.volkovskiyda.movieapp

import com.gmail.volkovskiyda.movieapp.app.AppNavigator
import com.gmail.volkovskiyda.movieapp.app.AppNotification
import com.gmail.volkovskiyda.movieapp.fetch.FetchMovieRepository
import com.gmail.volkovskiyda.movieapp.list.MovieListRepository
import com.gmail.volkovskiyda.movieapp.model.MovieStatus
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val selectedMovieRepository: SelectedMovieRepository,
    private val movieListRepository: MovieListRepository,
    private val fetchMovieRepository: FetchMovieRepository,
    private val appNavigator: AppNavigator,
    private val notification: AppNotification,
) {
    suspend fun selectMovieId(movieId: String) {
        selectedMovieRepository.select(MovieStatus.Loading)
        appNavigator.withNavController { navigate(R.id.openMovieDetails) }
        val movie = movieListRepository.getMovieList().firstOrNull()?.find { it.id == movieId }
            ?: fetchMovieRepository.fetch(movieId)
        val movieStatus = movie?.let(MovieStatus::Selected)?.also {
            notification.dismissMovieNotification(movieId)
        } ?: MovieStatus.NotFound
        selectedMovieRepository.select(movieStatus)
    }
}