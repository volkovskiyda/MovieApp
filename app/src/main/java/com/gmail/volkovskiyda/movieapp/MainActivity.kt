package com.gmail.volkovskiyda.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MovieListFragment.MovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, MovieListFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onMovieClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, MovieDetailsFragment())
            .addToBackStack(null)
            .commit()
    }
}