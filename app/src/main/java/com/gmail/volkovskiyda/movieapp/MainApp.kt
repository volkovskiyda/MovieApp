package com.gmail.volkovskiyda.movieapp

import android.app.Application
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(
            ImageLoader
                .Builder(this)
                .componentRegistry { add(FirebaseStorageReferenceFetcher) }
                .build()
        )
    }
}