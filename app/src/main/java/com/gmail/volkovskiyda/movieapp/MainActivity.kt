package com.gmail.volkovskiyda.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gmail.volkovskiyda.movieapp.app.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appNavigator.observeNavigation()
            .onEach { navigation -> navigation(findNavController(R.id.nav_host_fragment)) }
            .launchIn(lifecycleScope)
        lifecycleScope.launch {
            delay(100)
            handleIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null || intent.action != Intent.ACTION_VIEW) return
        val movieId = intent.data?.lastPathSegment ?: return
        appNavigator.withNavController { navigate(R.id.openMovie, bundleOf("movieId" to movieId)) }
    }
}