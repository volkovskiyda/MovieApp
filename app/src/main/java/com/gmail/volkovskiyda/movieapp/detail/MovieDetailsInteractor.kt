package com.gmail.volkovskiyda.movieapp.detail

import com.gmail.volkovskiyda.movieapp.app.AppNavigator
import com.gmail.volkovskiyda.movieapp.model.MovieStatus
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsInteractor @Inject constructor(
    private val repository: SelectedMovieRepository,
    private val appNavigator: AppNavigator
) {

    fun getMovieDetails(): Flow<MovieStatus> = repository.observe()

    fun popBackStack() = appNavigator.withNavController { popBackStack() }
}