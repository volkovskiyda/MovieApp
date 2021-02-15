package com.gmail.volkovskiyda.movieapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.MovieStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val interactor: MovieDetailsInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<MovieStatus>(MovieStatus.Loading)
    val state: Flow<MovieStatus> = _state

    init {
        interactor.getMovieDetails()
            .onEach { movie -> _state.value = movie }
            .launchIn(viewModelScope)
    }

    fun popBackStack() {
        interactor.popBackStack()
    }
}