package com.gmail.volkovskiyda.movieapp.db

import androidx.room.*
import com.gmail.volkovskiyda.movieapp.model.entity.ActorEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieActorCrossRefEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieCastEntity
import com.gmail.volkovskiyda.movieapp.model.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(list: List<ActorEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActors(list: List<MovieActorCrossRefEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM movie")
    fun movieCast(): Flow<List<MovieCastEntity>>
}