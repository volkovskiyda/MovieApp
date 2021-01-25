package com.gmail.volkovskiyda.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.volkovskiyda.movieapp.model.entity.ActorEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieActorEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieEntity

@Database(entities = [MovieEntity::class, ActorEntity::class, MovieActorEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}