package com.gmail.volkovskiyda.movieapp.selected

import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow

interface SelectedMovieRepository {
    fun observe(): Flow<Movie>
    fun select(movie: Movie)

    companion object {
        private var instance: SelectedMovieRepositoryImpl? = null
        fun instance(): SelectedMovieRepositoryImpl =
            instance ?: SelectedMovieRepositoryImpl().also { instance = it }
    }
}