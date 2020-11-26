package com.gmail.volkovskiyda.movieapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MovieListFragment : Fragment() {

    private var onMovieClickListener: MovieClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onMovieClickListener = context as? MovieClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.avengers_container).setOnClickListener {
            onMovieClickListener?.onMovieClicked()
        }
    }

    interface MovieClickListener {
        fun onMovieClicked()
    }
}