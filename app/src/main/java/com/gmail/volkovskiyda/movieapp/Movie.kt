package com.gmail.volkovskiyda.movieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String = "",
    val duration: String = "",
    val image: String = "",
    val imageBackground: String = "",
    val genre: String = "",
    val rating: String = "",
    val storyline: String = "",
    val actors: List<Actor> = emptyList(),
) : Parcelable