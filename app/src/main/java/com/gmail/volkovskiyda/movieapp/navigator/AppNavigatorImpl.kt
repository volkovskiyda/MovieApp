package com.gmail.volkovskiyda.movieapp.navigator

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import com.gmail.volkovskiyda.movieapp.ActivityQueue
import com.gmail.volkovskiyda.movieapp.R
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activityQueue: ActivityQueue
) : AppNavigator {

    override fun popBackStack() = navController { popBackStack() }

    override fun popBackStack(destinationId: Int, inclusive: Boolean) =
        navController { popBackStack(destinationId, inclusive) }

    override fun navigate(
        resId: Int,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) = navController { navigate(resId, args, navOptions, navigatorExtras) }

    private inline fun navController(crossinline action: NavController.() -> Unit) =
        activityQueue.add { action(findNavController(R.id.nav_host_fragment)) }
}