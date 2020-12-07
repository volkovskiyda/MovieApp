package com.gmail.volkovskiyda.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieListAdapter = MovieListAdapter()
        view.findViewById<RecyclerView>(R.id.movie_list).adapter = movieListAdapter
        DataSource.getMovieList()
            .onEach { movies -> movieListAdapter.submitList(movies) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }
}