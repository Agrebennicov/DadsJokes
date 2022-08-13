package com.agrebennicov.jetpackdemo.features.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.agrebennicov.jetpackdemo.common.ui.BottomNavBar
import com.agrebennicov.jetpackdemo.common.ui.CustomTopAppBar
import com.agrebennicov.jetpackdemo.common.ui.NoRippleTheme
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarState
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    state: State<MainState>,
    toolbarState: State<ToolbarState>,
    navController: NavHostController,
    graph: NavGraph,
    onCloseSelectionClick: () -> Unit,
    onShareClick: () -> Unit,
    onUnSaveJokes: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (state.value.showTopBar) {
                CustomTopAppBar(
                    state = toolbarState,
                    onCloseSelectionClick = onCloseSelectionClick,
                    onShareClick = onShareClick,
                    onUnSaveJokes = onUnSaveJokes
                )
            }
        },
        bottomBar = {
            if (state.value.showBottomBar) {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    BottomNavBar(navController = navController)
                }
            }
        },
    ) { paddingValues ->
        AnimatedNavHost(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface),
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            navController = navController,
            graph = graph
        )
    }
}