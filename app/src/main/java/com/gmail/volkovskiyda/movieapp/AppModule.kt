package com.gmail.volkovskiyda.movieapp

import com.gmail.volkovskiyda.movieapp.list.MovieListRepository
import com.gmail.volkovskiyda.movieapp.list.MovieListRepositoryImpl
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun selectedMovieRepository(impl: SelectedMovieRepositoryImpl): SelectedMovieRepository

    @Binds
    @Singleton
    abstract fun movieListRepository(impl: MovieListRepositoryImpl): MovieListRepository
}