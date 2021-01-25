package com.gmail.volkovskiyda.movieapp.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieCastEntity(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = ActorEntity::class,
        associateBy = Junction(
            MovieActorEntity::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<ActorEntity>
)