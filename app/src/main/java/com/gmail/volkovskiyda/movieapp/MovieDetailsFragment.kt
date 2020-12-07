package com.gmail.volkovskiyda.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>("movie")
            ?: throw IllegalArgumentException("No movie provided")
        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = movie.title
        view.findViewById<ImageView>(R.id.background)
            .load(Firebase.storage.getReference(movie.imageBackground))
        view.findViewById<TextView>(R.id.rating).text = movie.rating
        view.findViewById<TextView>(R.id.genre).text = movie.genre
        view.findViewById<TextView>(R.id.storyline).text = movie.storyline
        view.findViewById<RecyclerView>(R.id.cast).adapter = ActorListAdapter().also { adapter ->
            adapter.submitList(movie.actors)
        }
    }
}