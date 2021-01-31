package com.gmail.volkovskiyda.movieapp.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.setupReview
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { viewModel.popBackStack() }

        val adapter = ActorListAdapter()
        view.findViewById<RecyclerView>(R.id.cast).adapter = adapter

        val toolbarLayout = view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val background = view.findViewById<ImageView>(R.id.background)
        val genre = view.findViewById<TextView>(R.id.genre)
        val star1 = view.findViewById<ImageView>(R.id.star_1)
        val star2 = view.findViewById<ImageView>(R.id.star_2)
        val star3 = view.findViewById<ImageView>(R.id.star_3)
        val star4 = view.findViewById<ImageView>(R.id.star_4)
        val star5 = view.findViewById<ImageView>(R.id.star_5)
        val reviews = view.findViewById<TextView>(R.id.reviews)
        val storyline = view.findViewById<TextView>(R.id.storyline)
        val cast = view.findViewById<TextView>(R.id.cast_title)

        viewModel.state.onEach { movie ->
            toolbarLayout.title = movie.title
            background.load(movie.imageBackground)
            genre.text = movie.genre
            setupReview(star1, star2, star3, star4, star5, movie.review)
            reviews.text = reviews.resources.getString(R.string.reviews, movie.reviewCount)
            storyline.text = movie.storyline
            cast.isVisible = movie.actors.isNotEmpty()
            adapter.submitList(movie.actors)
        }.launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }
}