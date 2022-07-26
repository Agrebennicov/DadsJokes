package com.agrebennicov.jetpackdemo.common.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Using EntryTransition.None and ExitTransition.None still animate composables
 * So I'm using just regular fadeIn/Out with 0 duration to not have any animation when needed
 */
@OptIn(ExperimentalAnimationApi::class)
fun getNonAnimatedContentTransform() = fadeIn(animationSpec = tween(0)) with
        fadeOut(animationSpec = tween(0))