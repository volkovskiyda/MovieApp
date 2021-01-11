package com.gmail.volkovskiyda.movieapp.model.response.config


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: ConfigurationImagesResponse
)