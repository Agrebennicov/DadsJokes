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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ErrorPlaceholder(
    modifier: Modifier = Modifier,
    text: String? = null,
    @DrawableRes topImage: Int? = null,
    @DrawableRes centerImage: Int? = null,
    onTryAgainButtonClick: (() -> Unit)? = null,
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (dadImage, errorImage, errorText, tryAgainButton) = createRefs()

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
                    )
                    .constrainAs(errorImage) {
                        bottom.linkTo(dadImage.top)
                        start.linkTo(dadImage.start)
                        end.linkTo(dadImage.end)
                    },
                painter = painterResource(id = topImage), contentDescription = null
            )
        }

        if (centerImage != null) {
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(dadImage) { centerTo(parent) },
                painter = painterResource(id = centerImage),
                contentDescription = null
            )
        }

        if (text != null) {
            Text(
                modifier = modifier
                    .padding(8.dp)
                    .constrainAs(errorText) {
                        top.linkTo(dadImage.bottom)
                        start.linkTo(dadImage.start)
                        end.linkTo(dadImage.end)
                    },
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        }

        if (onTryAgainButtonClick != null) {
            CommonButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(tryAgainButton) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = stringResource(id = R.string.try_again),
                backGroundColor = MaterialTheme.colors.primaryVariant,
                onButtonClicked = onTryAgainButtonClick,
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
            text = stringResource(id = R.string.common_error)
        )
    }
}

@Preview
@Composable
fun ErrorPlaceholderTryAgainPreview() {
    JetpackDemoTheme {
        ErrorPlaceholder(
            topImage = R.drawable.ic_error,
            centerImage = R.drawable.ic_dad,
            text = stringResource(id = R.string.common_error),
            onTryAgainButtonClick = {}
        )
    }
}
