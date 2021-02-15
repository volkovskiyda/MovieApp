package com.gmail.volkovskiyda.movieapp.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val interactor: MovieInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.filterNotNull()

    fun setMovieId(movieId: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            _state.value = interactor.loadMovie(movieId)?.let(State::Show) ?: State.NotFound
        }
    }

    fun popBackStack() {
        interactor.popBackStack()
    }

    sealed class State {
        object Loading : State()
        object NotFound : State()
        data class Show(val movie: Movie) : State()
    }
}