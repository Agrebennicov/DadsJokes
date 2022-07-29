package com.agrebennicov.jetpackdemo.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.createGraph
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import com.agrebennicov.jetpackdemo.common.ui.BottomNavBar
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes
import com.agrebennicov.jetpackdemo.navigation.main.buildGraph
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDemoTheme {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    val viewModel: MainViewModel = hiltViewModel()
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colors.isLight
                    val statusBarColor = MaterialTheme.colors.primaryVariant
                    val navController = rememberAnimatedNavController()
                    val mainGraph = navController.createGraph(
                        startDestination = NavRoutes.RandomScreen.route,
                        route = NavRoutes.GRAPH_ROUTE,
                        builder = {
                            buildGraph(navController = navController)
                        }
                    )

                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = statusBarColor,
                            darkIcons = useDarkIcons
                        )
                    }

                    Column(modifier = Modifier.background(color = MaterialTheme.colors.surface)) {
                        AnimatedNavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            graph = mainGraph
                        )
                        Box {
                            BottomNavBar(
                                items = viewModel.state.value.items,
                                onItemSelected = {
                                    handleBottomBarItemSelection(
                                        navController = navController,
                                        newRoute = it
                                    )
                                    viewModel.onAction(MainAction.TabChanged(it))
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleBottomBarItemSelection(navController: NavController, newRoute: String) {
        navController.navigate(newRoute) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        0.0f,
        0.0f,
        0.0f,
        0.0f
    )
}