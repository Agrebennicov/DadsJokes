@file:OptIn(ExperimentalAnimationApi::class)

package com.agrebennicov.jetpackdemo.navigation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.agrebennicov.jetpackdemo.features.random.RandomAction
import com.agrebennicov.jetpackdemo.features.random.RandomScreen
import com.agrebennicov.jetpackdemo.features.random.RandomViewModel
import com.agrebennicov.jetpackdemo.features.search.SearchAction
import com.agrebennicov.jetpackdemo.features.search.SearchScreen
import com.agrebennicov.jetpackdemo.features.search.SearchViewModel
import com.agrebennicov.jetpackdemo.features.search.searchView.ScrollableSearchViewModel
import com.agrebennicov.jetpackdemo.features.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable

@Composable
fun NavGraphBuilder.BuildSplashNavigation(
    navController: NavController
) {
    navigation(
        route = DestinationRoutes.SplashDestination.route,
        startDestination = NavRoutes.SplashScreen.route,
        builder = {
            composable(NavRoutes.SplashScreen.route) {
                SplashScreen {
                    navController.navigate(DestinationRoutes.MainDestination.route) {
                        popUpTo(NavRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            }
        }
    )
}

@Composable
fun NavGraphBuilder.BuildMainNavigation() {
    val randomViewModel: RandomViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val scrollViewModel: ScrollableSearchViewModel = hiltViewModel()

    navigation(
        route = DestinationRoutes.MainDestination.route,
        startDestination = NavRoutes.RandomScreen.route,
        builder = {
            composable(NavRoutes.RandomScreen.route) {
                val context = LocalContext.current

                LaunchedEffect(key1 = this) {
                    randomViewModel.onAction(RandomAction.Refresh)
                }

                RandomScreen(
                    state = randomViewModel.state,
                    onSaveClick = { randomViewModel.onAction(RandomAction.SaveJoke(it)) },
                    onDeleteClick = { randomViewModel.onAction(RandomAction.DeleteJoke(it)) },
                    onShareClick = {
                        randomViewModel.onAction(RandomAction.ShareJoke(context, it))
                    },
                    onNextJokeClick = { randomViewModel.onAction(RandomAction.LoadNextJoke) },
                    onTryAgainButtonClick = { randomViewModel.onAction(RandomAction.LoadFirstJoke) },
                    onDownloadClicked = { randomViewModel.onAction(RandomAction.DownloadJoke(it)) },
                    onDownloadAnimationFinished = { randomViewModel.onAction(RandomAction.DownloadAnimationFinished) }
                )
            }

            composable(NavRoutes.SearchScreen.route) {
                val context = LocalContext.current

                LaunchedEffect(key1 = this) {
                    searchViewModel.onAction(SearchAction.Refresh)
                }

                SearchScreen(
                    state = searchViewModel.state,
                    scrollUpState = scrollViewModel.scrollUpState,
                    onQueryChanged = { searchViewModel.onAction(SearchAction.QueryChanged(it)) },
                    onSearchClicked = { searchViewModel.onAction(SearchAction.Search(it)) },
                    onShareClick = {
                        searchViewModel.onAction(SearchAction.ShareJoke(context, it))
                    },
                    onJokeSaved = { searchViewModel.onAction(SearchAction.JokeSaved(it)) },
                    onJokeUnsaved = { searchViewModel.onAction(SearchAction.JokeUnSaved(it)) },
                    onClearButtonClick = { searchViewModel.onAction(SearchAction.QueryChanged("")) },
                    onTryAgainButtonClick = { searchViewModel.onAction(SearchAction.QueryChanged("")) },
                    onFirstVisibleItemChanged = { scrollViewModel.updateScrollPosition(it) }
                )
            }

            composable(NavRoutes.SavedScreen.route) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center), text = "Saved"
                    )
                }
            }
        }
    )
}
