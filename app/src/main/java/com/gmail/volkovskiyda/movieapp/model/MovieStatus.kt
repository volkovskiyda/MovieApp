package com.gmail.volkovskiyda.movieapp.model

sealed class MovieStatus {
    object Loading : MovieStatus()
    object NotFound : MovieStatus()
    data class Selected(val movie: Movie) : MovieStatus()
}