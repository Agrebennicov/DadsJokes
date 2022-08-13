package com.agrebennicov.jetpackdemo.features.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
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

    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            LottieAnimation(
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp, 100.dp),
                composition = composition
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                text = stringResource(id = R.string.saved_to_gallery),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
@Preview
fun DownloadConfirmationPreview() {
    JetpackDemoTheme {
        Box(Modifier.fillMaxSize()) {
            DownloadConfirmation(onAnimationFinished = {})
        }
    }
}