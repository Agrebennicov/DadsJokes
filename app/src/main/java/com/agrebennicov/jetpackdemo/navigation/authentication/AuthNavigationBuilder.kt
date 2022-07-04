package com.agrebennicov.jetpackdemo.navigation.authentication

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.agrebennicov.jetpackdemo.features.welcome.WelcomeScreen
import com.agrebennicov.jetpackdemo.features.welcome.WelcomeViewModel

fun NavGraphBuilder.addAuthenticationGraph(
    modifier: Modifier = Modifier
) {
    composable(AuthNavRoutes.Welcome.route) {
        val viewModel: WelcomeViewModel = hiltViewModel()
        WelcomeScreen(viewModel.state, modifier = modifier)
    }
    composable(AuthNavRoutes.LoginScreen.route) {
//        LoginScreen()
    }
    composable(AuthNavRoutes.RegisterScreen.route) {
//        RegisterScreen()
    }
}