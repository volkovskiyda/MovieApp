package com.gmail.volkovskiyda.movieapp.selected

import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow

interface SelectedMovieRepository {
    fun observe(): Flow<Movie>
    fun select(movie: Movie)
}