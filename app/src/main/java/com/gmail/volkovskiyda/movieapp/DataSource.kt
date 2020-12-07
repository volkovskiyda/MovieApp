package com.gmail.volkovskiyda.movieapp

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

object DataSource {

    fun getMovieList(): Flow<List<Movie>> = Firebase.firestore.collection("movies").asFlow()
}