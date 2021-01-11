package com.gmail.volkovskiyda.movieapp.model.response.credit


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditListResponse(
    @SerialName("cast")
    val cast: List<CastResponse>,
    @SerialName("id")
    val id: Int
)