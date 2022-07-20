package com.agrebennicov.jetpackdemo.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    surface = Background,
    background = Background,
    primary = Accent
)

private val LightColorPalette = lightColors(
    surface = Background,
    background = Background,
    primary = Accent
)

@Composable
fun JetpackDemoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = CardShape,
        content = content
    )
}