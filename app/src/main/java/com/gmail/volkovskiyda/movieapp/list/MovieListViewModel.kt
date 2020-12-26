package com.gmail.volkovskiyda.movieapp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.selected.SelectedMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieListViewModel : ViewModel() {

    private val repository = MovieListRepositoryImpl()
    private val interactor = MovieListInteractor(repository, SelectedMovieRepository.instance())

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