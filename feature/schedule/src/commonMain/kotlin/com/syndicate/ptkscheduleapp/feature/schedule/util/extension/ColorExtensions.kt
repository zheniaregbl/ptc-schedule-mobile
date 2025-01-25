package com.syndicate.ptkscheduleapp.feature.schedule.util.extension

import androidx.compose.ui.graphics.Color
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.HslColor
import kotlin.math.max
import kotlin.math.min

internal fun Color.toHsl(): HslColor {
    val r = red
    val g = green
    val b = blue

    val max = max(r, max(g, b))
    val min = min(r, min(g, b))

    val h: Float
    val s: Float
    val l = (max + min) / 2

    if (max == min) {
        // achromatic
        h = 0f
        s = 0f
    } else {
        val d = max - min
        s = if (l > 0.5f) d / (2 - max - min) else d / (max + min)
        h = when (max) {
            r -> (g - b) / d + (if (g < b) 6 else 0)
            g -> (b - r) / d + 2
            b -> (r - g) / d + 4
            else -> 0f
        }
    }

    return HslColor(h * 60, s, l)
}