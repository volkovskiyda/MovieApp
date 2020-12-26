package com.gmail.volkovskiyda.movieapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsViewModel : ViewModel() {

    private val interactor = MovieDetailsInteractor(SelectedMovieRepository.instance())

    private val _state = MutableStateFlow<Movie?>(null)
    val state = _state.filterNotNull()

    init {
        interactor.getMovieDetails()
            .onEach { movie -> _state.value = movie }
            .launchIn(viewModelScope)
    }
}