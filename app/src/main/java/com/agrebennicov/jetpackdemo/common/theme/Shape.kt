package com.agrebennicov.jetpackdemo.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val CardShape = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(10.dp)
)

val TopCornerShape = Shapes(
    small = RoundedCornerShape(topStart = 10f, topEnd = 10f),
    medium = RoundedCornerShape(topStart = 10f, topEnd = 10f),
    large = RoundedCornerShape(topStart = 10f, topEnd = 10f)
)