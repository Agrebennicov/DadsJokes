package com.agrebennicov.jetpackdemo.features.main

import com.agrebennicov.jetpackdemo.common.BaseViewModel
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes

class MainViewModel : BaseViewModel<MainAction, MainState>(MainState()) {
    override fun onAction(action: MainAction) {
        updateState(action)
    }

    override fun reduce(action: MainAction, oldState: MainState): MainState {
        return when (action) {
            is MainAction.RouteChanged -> handleRouteChange(action.route)
        }
    }

    private fun handleRouteChange(route: String?): MainState {
        val showBottomBar: Boolean
        val showTopBar: Boolean
        when (route) {
            NavRoutes.SplashScreen.route -> {
                showTopBar = false
                showBottomBar = false
            }
            NavRoutes.RandomScreen.route -> {
                showTopBar = true
                showBottomBar = true
            }
            NavRoutes.SearchScreen.route -> {
                showTopBar = true
                showBottomBar = true
            }
            NavRoutes.SavedScreen.route -> {
                showTopBar = true
                showBottomBar = true
            }
            else -> {
                showTopBar = false
                showBottomBar = false
            }
        }
        return state.value.copy(
            showTopBar = showTopBar,
            showBottomBar = showBottomBar
        )
    }
}
