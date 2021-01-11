package com.gmail.volkovskiyda.movieapp.model.response.genre


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    @SerialName("genres")
    val genres: List<GenreResponse>
)