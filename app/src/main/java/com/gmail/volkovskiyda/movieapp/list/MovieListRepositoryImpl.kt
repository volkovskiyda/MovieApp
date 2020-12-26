package com.gmail.volkovskiyda.movieapp.list

import com.gmail.volkovskiyda.movieapp.asFlow
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class MovieListRepositoryImpl : MovieListRepository {

    override fun getMovieList(): Flow<List<Movie>> =
        Firebase.firestore.collection("movies").asFlow()
}