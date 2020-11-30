package com.gmail.volkovskiyda.movieapp

data class Movie(
    val title: String,
    val duration: String,
    val image: Int,
    val genre: String,
    val rating: String,
    val storyline: String,
    val actors: List<Actor>
)
