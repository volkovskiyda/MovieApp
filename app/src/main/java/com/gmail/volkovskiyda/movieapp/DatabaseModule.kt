package com.gmail.volkovskiyda.movieapp

import android.content.Context
import androidx.room.Room
import com.gmail.volkovskiyda.movieapp.db.MovieDao
import com.gmail.volkovskiyda.movieapp.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun movieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()

    @Provides
    @Singleton
    fun movieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()
}