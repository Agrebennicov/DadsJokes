package com.agrebennicov.jetpackdemo.features.random

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun DownloadConfirmation(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.download))
    val progress by animateLottieCompositionAsState(composition)
    if (progress == 1f) onAnimationFinished()

    Card(
        elevation = 16.dp,
        modifier = modifier.wrapContentSize(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LottieAnimation(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp, 100.dp),
                composition = composition
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Download Complete",
                style = MaterialTheme.typography.caption
            )
        }
    }
}
