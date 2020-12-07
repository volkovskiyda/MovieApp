package com.gmail.volkovskiyda.movieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val name: String = "",
    val image: String = "",
) : Parcelable