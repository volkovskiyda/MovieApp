package com.gmail.volkovskiyda.movieapp.fetch

import com.gmail.volkovskiyda.movieapp.model.Movie

interface FetchMovieRepository {
    suspend fun fetch(movieId: String): Movie?
}