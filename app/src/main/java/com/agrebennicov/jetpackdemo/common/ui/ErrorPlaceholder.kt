package com.agrebennicov.jetpackdemo.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.theme.JetpackDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

enum class ThreePartAnimation(val angle: Float, val delay: suspend CoroutineScope.() -> Unit) {
    NONE(0f, delay = { delay(500) }),
    FIRST(180f, delay = { delay(1000) }),
    SECOND(45f, delay = { delay(1000) }),
    THIRD(120f, delay = { delay(1000) })
}

@Composable
fun ErrorPlaceholder(
    text: String? = null,
    @DrawableRes topImage: Int? = null,
    @DrawableRes centerImage: Int? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (topImage != null) {
            var doAnimation: ThreePartAnimation by remember { mutableStateOf(ThreePartAnimation.NONE) }
            LaunchedEffect(key1 = Unit) {
                ThreePartAnimation.values().forEach { animation ->
                    doAnimation = animation
                    doAnimation.delay(this)
                }
            }
            val angle by animateFloatAsState(
                targetValue = doAnimation.angle,
                animationSpec = TweenSpec(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.2f,
                            pivotFractionY = 0.8f,
                        ),
                        rotationZ = angle,
                    ),
                painter = painterResource(id = topImage), contentDescription = null
            )
        }
        if (centerImage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Image(painter = painterResource(id = centerImage), contentDescription = null)
        }
        if (text != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview
@Composable
fun ErrorPlaceholderPreview() {
    JetpackDemoTheme {
        ErrorPlaceholder(
            topImage = R.drawable.ic_error,
            centerImage = R.drawable.ic_dad,
            text = "Dad is tired :( \nPlease try again another time"
        )
    }
}
