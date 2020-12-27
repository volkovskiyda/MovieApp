package com.gmail.volkovskiyda.movieapp

import com.gmail.volkovskiyda.movieapp.navigator.AppNavigator
import com.gmail.volkovskiyda.movieapp.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigatorModule {
    @Binds
    abstract fun appNavigator(impl: AppNavigatorImpl): AppNavigator
}