package com.gmail.volkovskiyda.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gmail.volkovskiyda.movieapp.app.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainInteractor: MainInteractor,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    fun observeNavigation(): Flow<NavController.() -> Unit> = appNavigator.observeNavigation()

    fun select(movieId: String) {
        viewModelScope.launch { mainInteractor.selectMovieId(movieId) }
    }
}