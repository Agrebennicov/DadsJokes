package com.agrebennicov.jetpackdemo.features.welcome

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(
    state: State<WelcomeState>,
    modifier: Modifier
) {
    Greeting(
        state.value.username,
        modifier
    )
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    val state = remember { mutableStateOf(WelcomeState()) }
    WelcomeScreen(state, Modifier)
}