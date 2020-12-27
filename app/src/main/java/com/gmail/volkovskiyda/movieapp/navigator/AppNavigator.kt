package com.gmail.volkovskiyda.movieapp.navigator

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

interface AppNavigator {
    fun popBackStack(): Boolean
    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean
    fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    )
}