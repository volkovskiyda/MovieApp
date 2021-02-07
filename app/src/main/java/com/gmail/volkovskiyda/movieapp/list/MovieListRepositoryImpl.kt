package com.gmail.volkovskiyda.movieapp.list

import androidx.work.*
import com.gmail.volkovskiyda.movieapp.MainWorker
import com.gmail.volkovskiyda.movieapp.db.MovieDao
import com.gmail.volkovskiyda.movieapp.model.Actor
import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val workManager: WorkManager,
) : MovieListRepository {



    override fun getMovieList(): Flow<List<Movie>> = movieDao.movieCast().map { list ->
        list.map { entity ->
            with(entity.movie) {
                Movie(
                    id = id,
                    title = title,
                    duration = duration,
                    image = image,
                    imageBackground = imageBackground,
                    genre = genre,
                    storyline = storyline,
                    actors = entity.actors.map { entity ->
                        with(entity) { Actor(id, name, image) }
                    },
                    review = review,
                    reviewCount = reviewCount
                )
            }
        }
    }.onStart {
        workManager.enqueueUniquePeriodicWork(
            "populateData",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<MainWorker>(6, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        )
    }
}