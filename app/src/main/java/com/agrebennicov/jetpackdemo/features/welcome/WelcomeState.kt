package com.agrebennicov.jetpackdemo.features.welcome

data class WelcomeState(
    val username: String = "Jetpack compose",
    val isLoggedIn: Boolean = false,
    val wasAnimationShown: Boolean = false
)