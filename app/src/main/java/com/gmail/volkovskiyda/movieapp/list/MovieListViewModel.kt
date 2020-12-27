package com.gmail.volkovskiyda.movieapp.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieListViewModel @ViewModelInject constructor(
    private val interactor: MovieListInteractor
) : ViewModel() {

    val state = MutableStateFlow(emptyList<Movie>())

    init {
        interactor.getMovieList()
            .onEach { movies -> state.value = movies }
            .launchIn(viewModelScope)
    }

    fun select(movie: Movie) {
        interactor.selectMovie(movie)
    }
}