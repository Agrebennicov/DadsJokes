package com.agrebennicov.jetpackdemo.navigation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.agrebennicov.jetpackdemo.features.random.RandomAction
import com.agrebennicov.jetpackdemo.features.random.RandomScreen
import com.agrebennicov.jetpackdemo.features.random.RandomViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.buildGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(NavRoutes.RandomScreen.route) {
        val viewModel: RandomViewModel = hiltViewModel()
        LaunchedEffect(key1 = Unit) { viewModel.onAction(RandomAction.LoadFirstJoke) }
        RandomScreen(
            state = viewModel.state,
            onSaveClick = { viewModel.onAction(RandomAction.SaveJoke) },
            onDeleteClick = { viewModel.onAction(RandomAction.DeleteJoke) },
            onShareClick = { },
            onNextJokeClick = { viewModel.onAction(RandomAction.LoadNextJoke) },
            onTryAgainButtonClick = { viewModel.onAction(RandomAction.LoadFirstJoke) }
        )
    }

    composable(NavRoutes.SearchScreen.route) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center), text = "Search"
            )
        }
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
