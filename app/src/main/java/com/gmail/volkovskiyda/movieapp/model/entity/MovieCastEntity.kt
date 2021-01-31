package com.gmail.volkovskiyda.movieapp.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieCastEntity(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            MovieActorCrossRefEntity::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<ActorEntity>,
)