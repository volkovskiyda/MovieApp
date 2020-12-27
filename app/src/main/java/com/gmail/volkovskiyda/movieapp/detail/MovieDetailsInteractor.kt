package com.gmail.volkovskiyda.movieapp.detail

import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.navigator.AppNavigator
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsInteractor @Inject constructor(
    private val repository: SelectedMovieRepository,
    private val appNavigator: AppNavigator
) {

    fun getMovieDetails(): Flow<Movie> = repository.observe()

    fun popBackStack() = appNavigator.popBackStack()
}