package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieListInteractor(
    private val repository: MovieListRepository,
    private val selectedMovieRepository: SelectedMovieRepository
) {

    fun getMovieList(): Flow<List<Movie>> = repository.getMovieList()

    fun selectMovie(movie: Movie) {
        selectedMovieRepository.select(movie)
    }
}