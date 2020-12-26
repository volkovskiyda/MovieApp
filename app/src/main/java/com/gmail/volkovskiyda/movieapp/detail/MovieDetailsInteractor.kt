package com.gmail.volkovskiyda.movieapp.detail

import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailsInteractor(private val repository: SelectedMovieRepository) {

    fun getMovieDetails(): Flow<Movie> = repository.observe()
}