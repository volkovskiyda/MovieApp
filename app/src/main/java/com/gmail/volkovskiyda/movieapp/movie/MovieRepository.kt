package com.gmail.volkovskiyda.movieapp.movie

import com.gmail.volkovskiyda.movieapp.model.Movie

interface MovieRepository {
    suspend fun fetchMovie(movieId: String): Movie?
}