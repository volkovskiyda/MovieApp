package com.gmail.volkovskiyda.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MovieListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieListAdapter = MovieListAdapter()
        view.findViewById<RecyclerView>(R.id.movie_list).adapter = movieListAdapter
        val avengerActors = listOf(
            Actor(getString(R.string.actor_robert), R.drawable.actor_robert)
        )
        val movies = listOf(
            Movie(
                getString(R.string.movie_list_avengers_end_game),
                getString(R.string.avengers_duration),
                R.drawable.movie_avengers,
                getString(R.string.action_adventure_fantasy),
                getString(R.string.rating_13),
                getString(R.string.storyline_avengers),
                avengerActors
            )
        )
        movieListAdapter.submitList(movies)
//        view.findViewById<View>(R.id.avengers_container).setOnClickListener {
//            activity?.let { activity -> (activity as MovieClickListener).onMovieClicked() }
//        }
    }

    interface MovieClickListener {
        fun onMovieClicked()
    }
}