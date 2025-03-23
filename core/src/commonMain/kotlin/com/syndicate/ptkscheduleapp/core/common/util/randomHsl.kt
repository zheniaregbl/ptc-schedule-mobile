package com.syndicate.ptkscheduleapp.core.common.util

import androidx.compose.ui.graphics.Color
import com.syndicate.ptkscheduleapp.core.common.util.extension.toHsl

fun randomHsl() = Color(
    red = (0..255).random(),
    green = (0..255).random(),
    blue = (0..255).random(),
    alpha = 255
).toHsl()