package com.gmail.volkovskiyda.movieapp.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.gmail.volkovskiyda.movieapp.work.main.MainPeriodicWorkRequest
import com.gmail.volkovskiyda.movieapp.work.main.MainWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit.HOURS
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkModule {
    @Provides
    @Singleton
    fun workManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    @NetworkConstraints
    fun networkConstraints(): Constraints =
        Constraints.Builder().setRequiredNetworkType(CONNECTED).build()

    @Provides
    @Singleton
    @MainPeriodicWorkRequest
    fun mainPeriodicWorkRequest(@NetworkConstraints constraints: Constraints): PeriodicWorkRequest =
        PeriodicWorkRequestBuilder<MainWorker>(6, HOURS).setConstraints(constraints).build()
}