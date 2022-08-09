package com.agrebennicov.jetpackdemo.navigation.main

sealed class NavRoutes(val route: String) {
    object SplashScreen : NavRoutes("splashRoute")
    object RandomScreen : NavRoutes("randomRoute")
    object SearchScreen : NavRoutes("searchRoute")
    object SavedScreen : NavRoutes("savedRoute")

    companion object {
        const val APP_GRAPH_ROUTE = "appGraphRoute"
    }
}

sealed class DestinationRoutes(val route: String) {
    object SplashDestination : DestinationRoutes("splashDestination")
    object MainDestination : DestinationRoutes("mainDestination")
}