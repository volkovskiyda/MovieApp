package com.gmail.volkovskiyda.movieapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val duration: String = "",
    val image: String = "",
    val imageBackground: String = "",
    val genre: String = "",
    val storyline: String = "",
    val review: Int,
    val reviewCount: Int,
)