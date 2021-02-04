package com.gmail.volkovskiyda.movieapp

import com.gmail.volkovskiyda.movieapp.list.MovieListRepository
import com.gmail.volkovskiyda.movieapp.list.MovieListRepositoryImpl
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun selectedMovieRepository(impl: SelectedMovieRepositoryImpl): SelectedMovieRepository

    @Binds
    @Singleton
    fun movieListRepository(impl: MovieListRepositoryImpl): MovieListRepository
}