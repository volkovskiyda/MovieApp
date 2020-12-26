package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getMovieList(): Flow<List<Movie>>
}