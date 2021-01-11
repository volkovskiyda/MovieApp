package com.gmail.volkovskiyda.movieapp.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Movie
import com.gmail.volkovskiyda.movieapp.setupReview

class MovieListAdapter(
    private val movieListViewModel: MovieListViewModel
) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder) {
            title.text = movie.title
            duration.text = movie.duration
            genre.text = movie.genre
            rating.text = movie.rating
            image.load(movie.image)
            setupReview(star1, star2, star3, star4, star5, movie.review)
            reviews.text = reviews.resources.getString(R.string.reviews, movie.reviewCount)
            itemView.setOnClickListener { movieListViewModel.select(movie) }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val duration: TextView = itemView.findViewById(R.id.duration)
        val genre: TextView = itemView.findViewById(R.id.genre)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val image: ImageView = itemView.findViewById(R.id.image)
        val star1: ImageView = itemView.findViewById(R.id.star_1)
        val star2: ImageView = itemView.findViewById(R.id.star_2)
        val star3: ImageView = itemView.findViewById(R.id.star_3)
        val star4: ImageView = itemView.findViewById(R.id.star_4)
        val star5: ImageView = itemView.findViewById(R.id.star_5)
        val reviews: TextView = itemView.findViewById(R.id.reviews)
    }

    object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}