package com.agrebennicov.jetpackdemo.common.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.agrebennicov.jetpackdemo.R

val Fonts = FontFamily(
    Font(R.font.baloo_regular, FontWeight.Normal),
    Font(R.font.baloo_medium, FontWeight.Medium),
    Font(R.font.baloo_semi_bold, FontWeight.SemiBold),
    Font(R.font.baloo_bold, FontWeight.Bold),
    Font(R.font.baloo_extra_bold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = TextHeader
    ),
    h2 = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = TextPrimary
    ),
    body1 = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextPrimary
    ),
    body2 = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextSecondary
    ),
    subtitle1 = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = TextPrimary,
        lineHeight = 16.sp
    ),
    caption = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = TextSecondary
    ),
    button = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextButton
    )
)