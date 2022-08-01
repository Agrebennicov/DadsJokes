package com.agrebennicov.jetpackdemo.navigation.main

import com.agrebennicov.jetpackdemo.R

sealed class NavRoutes(
    val route: String,
    val toolbarTitle: Int
) {
    object RandomScreen : NavRoutes("random", R.string.random_joke)
    object SearchScreen : NavRoutes("search", R.string.search)
    object RandomImageScreen : NavRoutes("randomImage", R.string.random_image_joke)
    object SavedScreen : NavRoutes("saved", R.string.saved)

    companion object {
        const val GRAPH_ROUTE = "mainGraphRoute"
    }
}