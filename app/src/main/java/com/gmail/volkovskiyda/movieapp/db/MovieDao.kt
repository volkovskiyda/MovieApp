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
    suspend fun insertMovies(entities: List<MovieEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(entities: List<ActorEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesActors(entities: List<MovieActorCrossRefEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM movie")
    fun movieCast(): Flow<List<MovieCastEntity>>

    @Query("DELETE FROM movie")
    suspend fun deleteMovies()

    @Query("DELETE FROM actor")
    suspend fun deleteActors()

    @Query("DELETE FROM movie_actor")
    suspend fun deleteMoviesActors()

    @Transaction
    suspend fun replace(
        movies: List<MovieEntity>,
        actors: List<ActorEntity>,
        movieActors: List<MovieActorCrossRefEntity>
    ) {
        deleteMovies()
        deleteActors()
        deleteMoviesActors()

        insertMovies(movies)
        insertActors(actors)
        insertMoviesActors(movieActors)
    }
}