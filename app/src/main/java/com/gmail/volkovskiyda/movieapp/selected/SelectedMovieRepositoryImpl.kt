package com.gmail.volkovskiyda.movieapp.selected

import com.gmail.volkovskiyda.movieapp.model.MovieStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SelectedMovieRepositoryImpl @Inject constructor() : SelectedMovieRepository {

    private val selected = MutableStateFlow<MovieStatus>(MovieStatus.Loading)

    override fun observe(): Flow<MovieStatus> = selected

    override fun select(movieStatus: MovieStatus) {
        selected.value = movieStatus
    }
}