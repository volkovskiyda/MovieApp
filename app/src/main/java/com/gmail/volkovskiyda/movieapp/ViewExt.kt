package com.gmail.volkovskiyda.movieapp

import android.widget.ImageView

fun setupReview(
    star1: ImageView,
    star2: ImageView,
    star3: ImageView,
    star4: ImageView,
    star5: ImageView,
    review: Int
) {
    star1.setImageResource(if (review < 1) R.drawable.ic_star else R.drawable.ic_star_fill)
    star2.setImageResource(if (review < 2) R.drawable.ic_star else R.drawable.ic_star_fill)
    star3.setImageResource(if (review < 3) R.drawable.ic_star else R.drawable.ic_star_fill)
    star4.setImageResource(if (review < 4) R.drawable.ic_star else R.drawable.ic_star_fill)
    star5.setImageResource(if (review < 5) R.drawable.ic_star else R.drawable.ic_star_fill)
}