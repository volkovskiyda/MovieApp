package com.gmail.volkovskiyda.movieapp.list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Error
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = MovieListAdapter(viewModel)
        view.findViewById<RecyclerView>(R.id.movie_list).adapter = adapter
        viewModel.state
            .onEach { movies -> adapter.submitList(movies) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
        viewModel.errorState
            .onEach { error -> error.show(requireContext()) }
            .launchIn(viewLifecycleOwner.lifecycle.coroutineScope)
    }

    private fun Error.show(context: Context) =
        Toast.makeText(context, toText(context), Toast.LENGTH_SHORT).show()
}