package com.agrebennicov.jetpackdemo.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
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

                Column {
                    AnimatedNavHost(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        graph = mainGraph
                    )
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

    private fun handleBottomBarItemSelection(navController: NavController, newRoute: String) {
        navController.navigate(newRoute) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
