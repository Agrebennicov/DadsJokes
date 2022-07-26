package com.agrebennicov.jetpackdemo.navigation.main

sealed class NavRoutes(val route: String) {
    object RandomScreen : NavRoutes("random")
    object SearchScreen : NavRoutes("search")
    object RandomImageScreen : NavRoutes("randomImage")
    object SavedScreen : NavRoutes("saved")

    companion object {
        const val GRAPH_ROUTE = "mainGraphRoute"
    }
}