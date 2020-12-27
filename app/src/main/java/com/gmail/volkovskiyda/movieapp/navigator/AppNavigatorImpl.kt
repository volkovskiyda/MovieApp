package com.gmail.volkovskiyda.movieapp.navigator

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import com.gmail.volkovskiyda.movieapp.R
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: FragmentActivity
) : AppNavigator {

    override fun popBackStack(): Boolean = navController().popBackStack()

    override fun popBackStack(destinationId: Int, inclusive: Boolean): Boolean =
        navController().popBackStack(destinationId, inclusive)

    override fun navigate(
        resId: Int,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) = navController().navigate(resId, args, navOptions, navigatorExtras)

    private fun navController(): NavController = activity.findNavController(R.id.nav_host_fragment)
}