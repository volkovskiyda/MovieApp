package com.gmail.volkovskiyda.movieapp.app

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.gmail.volkovskiyda.movieapp.MainActivity
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNotification @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)
    private val movieChannelId = "MovieChannel"
    private val movieTag = "Movie"

    fun showMovieNotification(movie: Movie) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    movieChannelId,
                    context.getString(R.string.app_name),
                    NotificationManagerCompat.IMPORTANCE_DEFAULT
                )
            )
        }
        val contentUri = "https://github.com/volkovskiyda/MovieApp/movie/${movie.id}/".toUri()

        val notification = NotificationCompat.Builder(context, movieChannelId)
            .setContentTitle(movie.title)
            .setContentText(movie.storyline)
            .setSmallIcon(R.drawable.ic_crosshair)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    1,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

        notificationManager.notify(movieTag, movie.id.toInt(), notification)
    }

    fun dismissMovieNotification(movieId: String) {
        notificationManager.cancel(movieTag, movieId.toInt())
    }
}