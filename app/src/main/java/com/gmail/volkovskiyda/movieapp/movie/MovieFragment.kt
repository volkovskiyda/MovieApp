package com.gmail.volkovskiyda.movieapp.movie

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.detail.ActorListAdapter
import com.gmail.volkovskiyda.movieapp.setupReview
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getString("movieId") ?: viewModel.popBackStack().run { return }
        viewModel.setMovieId(movieId)

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

        val contentScroll = view.findViewById<NestedScrollView>(R.id.content_scroll)
        val content = view.findViewById<View>(R.id.content)
        val loading = view.findViewById<View>(R.id.loading)
        val error = view.findViewById<View>(R.id.error)

        viewModel.state.onEach { movieState ->
            loading.isInvisible = movieState !is MovieViewModel.State.Loading
            error.isInvisible = movieState !is MovieViewModel.State.NotFound
            content.isInvisible = movieState !is MovieViewModel.State.Show
            contentScroll.isNestedScrollingEnabled = movieState is MovieViewModel.State.Show

            val movie = (movieState as? MovieViewModel.State.Show)?.movie ?: return@onEach

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