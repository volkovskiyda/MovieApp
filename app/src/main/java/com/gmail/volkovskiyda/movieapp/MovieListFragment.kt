package com.gmail.volkovskiyda.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieListAdapter = MovieListAdapter()
        view.findViewById<RecyclerView>(R.id.movie_list).adapter = movieListAdapter
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            val movies = DataSource.getMovieList()
            movieListAdapter.submitList(movies)
        }
    }
}