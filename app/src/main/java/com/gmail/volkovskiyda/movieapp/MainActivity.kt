package com.gmail.volkovskiyda.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeNavigation()
            .onEach { navigation -> navigation(findNavController(R.id.nav_host_fragment)) }
            .launchIn(lifecycleScope)
        lifecycleScope.launch {
            delay(1)
            handleIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        selectMovie(intent)
    }

    private fun selectMovie(intent: Intent?) {
        val movieId = intent?.data?.lastPathSegment
            ?.takeIf { intent.action == Intent.ACTION_VIEW } ?: return
        viewModel.select(movieId)
    }
}