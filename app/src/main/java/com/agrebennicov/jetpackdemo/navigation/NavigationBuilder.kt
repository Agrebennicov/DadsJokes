@file:OptIn(ExperimentalAnimationApi::class)

package com.agrebennicov.jetpackdemo.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.agrebennicov.jetpackdemo.features.main.MainViewModel
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarAction
import com.agrebennicov.jetpackdemo.features.main.toolbar.ToolbarViewModel
import com.agrebennicov.jetpackdemo.features.random.RandomAction
import com.agrebennicov.jetpackdemo.features.random.RandomScreen
import com.agrebennicov.jetpackdemo.features.random.RandomViewModel
import com.agrebennicov.jetpackdemo.features.saved.SavedAction
import com.agrebennicov.jetpackdemo.features.saved.SavedScreen
import com.agrebennicov.jetpackdemo.features.saved.SavedViewModel
import com.agrebennicov.jetpackdemo.features.search.SearchAction
import com.agrebennicov.jetpackdemo.features.search.SearchScreen
import com.agrebennicov.jetpackdemo.features.search.SearchViewModel
import com.agrebennicov.jetpackdemo.features.splash.SplashScreen
import com.agrebennicov.jetpackdemo.navigation.main.DestinationRoutes
import com.agrebennicov.jetpackdemo.navigation.main.NavRoutes
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
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
fun NavGraphBuilder.BuildMainNavigation(
    mainViewModel: MainViewModel,
    toolbarViewModel: ToolbarViewModel
) {
    val randomViewModel: RandomViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val savedViewModel: SavedViewModel = hiltViewModel()

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
                    scrollUpState = searchViewModel.scrollUpState,
                    onQueryChanged = { searchViewModel.onAction(SearchAction.QueryChanged(it)) },
                    onSearchClicked = { searchViewModel.onAction(SearchAction.Search(it)) },
                    onShareClick = {
                        searchViewModel.onAction(SearchAction.ShareJoke(context, it))
                    },
                    onJokeSaved = { searchViewModel.onAction(SearchAction.JokeSaved(it)) },
                    onJokeUnsaved = { searchViewModel.onAction(SearchAction.JokeUnSaved(it)) },
                    onClearButtonClick = { searchViewModel.onAction(SearchAction.QueryChanged("")) },
                    onTryAgainButtonClick = { searchViewModel.onAction(SearchAction.QueryChanged("")) },
                    onFirstVisibleItemChanged = { searchViewModel.updateScrollPosition(it) }
                )
            }

            composable(NavRoutes.SavedScreen.route) {
                val context = LocalContext.current

                LaunchedEffect(key1 = this) {
                    savedViewModel.onAction(SavedAction.Refresh)
                }

                LaunchedEffect(key1 = toolbarViewModel.state.value) {
                    val toolbarState = toolbarViewModel.state.value
                    when {
                        toolbarState.closeSelections -> {
                            savedViewModel.onAction(SavedAction.SelectionClosed)
                            toolbarViewModel.onAction(ToolbarAction.ResetToDefault)
                        }
                        toolbarState.shareSelections -> {
                            savedViewModel.onAction(
                                SavedAction.ShareJoke(
                                    context = context,
                                    jokes = savedViewModel.state.value.jokes.filter { it.isSelected }
                                )
                            )
                            toolbarViewModel.onAction(ToolbarAction.ResetToDefault)
                        }
                        toolbarState.unSaveSelections -> {
                            savedViewModel.onAction(SavedAction.UnSaveSelected)
                            toolbarViewModel.onAction(ToolbarAction.ResetToDefault)
                        }
                    }
                }

                SavedScreen(
                    state = savedViewModel.state,
                    onShareClick = {
                        savedViewModel.onAction(SavedAction.ShareJoke(context, listOf(it)))
                    },
                    onUnSaveClick = { savedViewModel.onAction(SavedAction.UnSaveJoke(it)) },
                    onUnSaveConfirmClick = {
                        savedViewModel.onAction(SavedAction.UnSaveJokeConfirmation(it))
                    },
                    onUnSaveCancelClick = { savedViewModel.onAction(SavedAction.UnSaveJokeCancel) },
                    onSelectJoke = {
                        savedViewModel.onAction(SavedAction.JokeSelected(it))
                        val selectedJokesCount = savedViewModel.state.value.jokes.count { joke ->
                            joke.isSelected
                        }
                        toolbarViewModel.onAction(
                            ToolbarAction.UpdateSelectingCount(selectedJokesCount)
                        )
                    },
                    onExitSelectionMode = {
                        savedViewModel.onAction(SavedAction.SelectionClosed)
                        toolbarViewModel.onAction(ToolbarAction.SelectingModeOff)
                    },
                    onEnterSelectionMode = {
                        savedViewModel.onAction(SavedAction.SelectionOpened)
                        toolbarViewModel.onAction(ToolbarAction.SelectingModeOn)
                    }
                )
            }
        }
    )
}
