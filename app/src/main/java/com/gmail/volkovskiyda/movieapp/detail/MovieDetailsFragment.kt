package com.gmail.volkovskiyda.movieapp.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.load
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.onEach { movie ->
            view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = movie.title
            view.findViewById<ImageView>(R.id.background)
                .load(Firebase.storage.getReference(movie.imageBackground))
            view.findViewById<TextView>(R.id.rating).text = movie.rating
            val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            view.findViewById<TextView>(R.id.genre).text = movie.genre
            view.findViewById<TextView>(R.id.storyline).text = movie.storyline
            view.findViewById<RecyclerView>(R.id.cast).adapter =
                ActorListAdapter().also { adapter ->
                    adapter.submitList(movie.actors)
                }
        }.launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }
}