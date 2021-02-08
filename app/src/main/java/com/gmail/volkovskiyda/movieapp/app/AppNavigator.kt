package com.gmail.volkovskiyda.movieapp.app

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() {
    private val navigation = MutableStateFlow<(NavController.() -> Unit)?>(null)

    fun observeNavigation() = navigation.drop(1).filterNotNull()

    fun withNavController(action: NavController.() -> Unit) {
        navigation.value = { action() }
    }
}