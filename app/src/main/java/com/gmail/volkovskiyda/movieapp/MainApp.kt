package com.gmail.volkovskiyda.movieapp

import android.app.Application
import coil.Coil
import coil.ImageLoader

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