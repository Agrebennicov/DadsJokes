package com.agrebennicov.jetpackdemo.navigation.authentication

sealed class AuthNavRoutes(val route: String) {
    object Welcome : AuthNavRoutes("welcome")
    object LoginScreen : AuthNavRoutes("login")
    object RegisterScreen : AuthNavRoutes("register")

    companion object {
        const val AUTH_GRAPH_ROUTE = "auth"
    }
}