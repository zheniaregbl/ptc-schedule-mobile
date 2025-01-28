package com.syndicate.ptkscheduleapp.feature.schedule.common.util

import androidx.compose.ui.graphics.Color
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.toHsl

internal fun randomHsl() = Color(
    red = (0..255).random(),
    green = (0..255).random(),
    blue = (0..255).random(),
    alpha = 255
).toHsl()