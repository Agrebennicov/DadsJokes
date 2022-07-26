package com.agrebennicov.jetpackdemo.common.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme

@Composable
fun Loader() {
    val infiniteTransition = rememberInfiniteTransition()
    val visibility by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes { durationMillis = 1000 },
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {}) {
        Image(
            modifier = Modifier
                .alpha(visibility)
                .align(Alignment.Center),
            painter = painterResource(R.drawable.ic_dad),
            contentDescription = "Dad"
        )
    }
}

@Preview
@Composable
fun LoaderPreview() {
    JetpackDemoTheme {
        Loader()
    }
}
