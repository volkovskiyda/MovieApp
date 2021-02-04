package com.gmail.volkovskiyda.movieapp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.Error
import com.gmail.volkovskiyda.movieapp.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val interactor: MovieListInteractor
) : ViewModel() {

    val state = MutableStateFlow(emptyList<Movie>())
    val errorState = MutableSharedFlow<Error>()

    init {
        interactor.getMovieList()
            .onEach { movies -> state.value = movies }
            .launchIn(viewModelScope)
        interactor.observeErrors()
            .onEach { error -> errorState.emit(error) }
            .launchIn(viewModelScope)
    }

    fun select(movie: Movie) {
        interactor.selectMovie(movie)
    }
}