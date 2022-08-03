@file:OptIn(ExperimentalAnimationApi::class)

package com.agrebennicov.jetpackdemo.common.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with

/**
 * Using EntryTransition.None and ExitTransition.None still animate composables
 * So I'm using just regular fadeIn/Out with 0 duration to not have any animation when needed
 */
fun getNonAnimatedContentTransform() = fadeIn(animationSpec = tween(0)) with
        fadeOut(animationSpec = tween(0))


fun getAppDefaultAnimation() = fadeIn(animationSpec = tween(350, delayMillis = 180)) with
        fadeOut(animationSpec = tween(180))