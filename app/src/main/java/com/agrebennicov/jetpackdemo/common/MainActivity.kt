package com.agrebennicov.jetpackdemo.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.agrebennicov.jetpackdemo.navigation.authentication.AuthNavRoutes
import com.agrebennicov.jetpackdemo.navigation.authentication.addAuthenticationGraph
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authGraph = navController.createGraph(
                startDestination = AuthNavRoutes.Welcome.route,
                route = AuthNavRoutes.AUTH_GRAPH_ROUTE,
                builder = { addAuthenticationGraph() }
            )
            JetpackDemoTheme {
                NavHost(
                    navController = navController,
                    authGraph
                )
            }
        }
    }
}
