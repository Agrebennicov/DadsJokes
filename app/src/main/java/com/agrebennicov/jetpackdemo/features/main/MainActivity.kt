package com.agrebennicov.jetpackdemo.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.createGraph
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarAction
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarViewModel
import com.agrebennicov.jetpackdemo.navigation.BuildMainNavigation
import com.agrebennicov.jetpackdemo.navigation.BuildSplashNavigation
import com.agrebennicov.jetpackdemo.navigation.main.DestinationRoutes
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDemoTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val toolbarViewModel: ToolbarViewModel = hiltViewModel()
                val navController = rememberAnimatedNavController()
                val route = navController.currentBackStackEntryAsState().value?.destination?.route
                LaunchedEffect(route) {
                    viewModel.onAction(MainAction.RouteChanged(route))
                    toolbarViewModel.onAction(ToolbarAction.RouteChanged(route))
                }
                val graph = navController.createGraph(
                    startDestination = DestinationRoutes.SplashDestination.route,
                    route = NavRoutes.APP_GRAPH_ROUTE,
                    builder = {
                        BuildSplashNavigation(navController)
                        BuildMainNavigation(viewModel, toolbarViewModel)
                    }
                )

                MainScreen(
                    state = viewModel.state,
                    toolbarState = toolbarViewModel.state,
                    navController = navController,
                    graph = graph,
                    onCloseSelectionClick = {
                        toolbarViewModel.onAction(ToolbarAction.SelectingModeOff)
                    },
                    onShareClick = {
                        toolbarViewModel.onAction(ToolbarAction.ShareSelections)
                    },
                    onUnSaveJokes = {
                        toolbarViewModel.onAction(ToolbarAction.UnSaveSelections)
                    },
                )
            }
        }
    }
}
