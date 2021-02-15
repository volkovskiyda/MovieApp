package com.gmail.volkovskiyda.movieapp.model.response.movie.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("name")
    val name: String
)