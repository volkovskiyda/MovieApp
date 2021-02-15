package com.gmail.volkovskiyda.movieapp.selected

import com.gmail.volkovskiyda.movieapp.model.MovieStatus
import kotlinx.coroutines.flow.Flow

interface SelectedMovieRepository {
    fun observe(): Flow<MovieStatus>
    fun select(movieStatus: MovieStatus)
}