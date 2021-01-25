package com.gmail.volkovskiyda.movieapp.model.entity

import androidx.room.Entity

@Entity(tableName = "movie_actor", primaryKeys = ["movieId", "actorId"])
data class MovieActorEntity(val movieId: String, val actorId: String)