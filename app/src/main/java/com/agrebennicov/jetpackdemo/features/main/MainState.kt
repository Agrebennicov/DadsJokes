package com.agrebennicov.jetpackdemo.features.main


data class MainState(
    val showTopBar: Boolean = false,
    val showBottomBar: Boolean = false
)

sealed class MainAction {
    data class RouteChanged(val route: String?) : MainAction()
}
