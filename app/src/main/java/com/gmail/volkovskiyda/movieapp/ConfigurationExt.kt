package com.gmail.volkovskiyda.movieapp

import com.gmail.volkovskiyda.movieapp.model.response.config.ConfigurationResponse

fun ConfigurationResponse.image(path: String): String =
    "${images.secureBaseUrl}${last(images.posterSizes)}$path"

fun ConfigurationResponse.imageBackground(path: String): String =
    "${images.secureBaseUrl}${last(images.backdropSizes)}$path"

fun ConfigurationResponse.profile(path: String): String =
    "${images.secureBaseUrl}${last(images.profileSizes)}$path"

private fun last(list: List<String>) = list.dropLast(1).last()