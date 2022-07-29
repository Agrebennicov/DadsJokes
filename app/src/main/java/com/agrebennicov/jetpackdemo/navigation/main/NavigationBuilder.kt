package com.agrebennicov.jetpackdemo.navigation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.agrebennicov.jetpackdemo.features.random.RandomAction
import com.agrebennicov.jetpackdemo.features.random.RandomScreen
import com.agrebennicov.jetpackdemo.features.random.RandomViewModel
import com.agrebennicov.jetpackdemo.features.search.SearchAction
import com.agrebennicov.jetpackdemo.features.search.SearchScreen
import com.agrebennicov.jetpackdemo.features.search.SearchViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(NavRoutes.RandomScreen.route) {
        val viewModel: RandomViewModel = hiltViewModel()
        RandomScreen(
            state = viewModel.state,
            onSaveClick = { viewModel.onAction(RandomAction.SaveJoke(it)) },
            onDeleteClick = { viewModel.onAction(RandomAction.DeleteJoke(it)) },
            onShareClick = { },
            onNextJokeClick = { viewModel.onAction(RandomAction.LoadNextJoke) },
            onTryAgainButtonClick = { viewModel.onAction(RandomAction.LoadFirstJoke) }
        )
    }

    composable(NavRoutes.SearchScreen.route) {
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(
            state = viewModel.state,
            onQueryChanged = { viewModel.onAction(SearchAction.QueryChanged(it)) },
            onSearchClicked = { viewModel.onAction(SearchAction.Search(it)) },
            onJokeSaved = { viewModel.onAction(SearchAction.JokeSaved(it)) },
            onJokeUnsaved = { viewModel.onAction(SearchAction.JokeUnSaved(it)) },
            onClearButtonClick = { viewModel.onAction(SearchAction.QueryChanged("")) },
            onTryAgainButtonClick = { viewModel.onAction(SearchAction.QueryChanged("")) }
        )
    }

    composable(NavRoutes.RandomImageScreen.route) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center), text = "Random Image"
            )
        }
    }

    composable(NavRoutes.SavedScreen.route) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center), text = "Saved"
            )
        }
    }
}
