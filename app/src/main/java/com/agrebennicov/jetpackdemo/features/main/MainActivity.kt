package com.agrebennicov.jetpackdemo.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.createGraph
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import com.agrebennicov.jetpackdemo.common.ui.BottomNavBar
import com.agrebennicov.jetpackdemo.common.ui.NoRippleTheme
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
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight
                val statusBarColor = MaterialTheme.colors.primaryVariant
                val navController = rememberAnimatedNavController()
                val currentRoute = navController.currentBackStackEntryAsState()
                val mainGraph = navController.createGraph(
                    startDestination = NavRoutes.RandomScreen.route,
                    route = NavRoutes.GRAPH_ROUTE,
                    builder = { buildGraph(navController = navController) }
                )

                val toolbarTitle = when (currentRoute.value?.destination?.route) {
                    NavRoutes.RandomScreen.route -> R.string.random_joke
                    NavRoutes.SearchScreen.route -> R.string.search
                    NavRoutes.SavedScreen.route -> R.string.saved
                    else -> null
                }

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = useDarkIcons
                    )
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            contentPadding = PaddingValues(start = 16.dp),
                            elevation = 16.dp
                        ) {
                            toolbarTitle?.let {
                                Text(
                                    style = MaterialTheme.typography.h1,
                                    text = stringResource(id = it)
                                )
                            }
                        }
                    },
                    bottomBar = {
                        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) { paddingValues ->
                    AnimatedNavHost(
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding())
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.surface),
                        navController = navController,
                        graph = mainGraph
                    )
                }
            }
        }
    }
}
