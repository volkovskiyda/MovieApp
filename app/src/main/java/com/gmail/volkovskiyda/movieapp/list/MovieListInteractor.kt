package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.AppNavigator
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListInteractor @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val selectedMovieRepository: SelectedMovieRepository,
    private val appNavigator: AppNavigator
) {

    fun getMovieList(): Flow<List<Movie>> = movieListRepository.getMovieList()

    fun selectMovie(movie: Movie) {
        selectedMovieRepository.select(movie)
        appNavigator.withNavController { navigate(R.id.openMovieDetails) }
    }
}