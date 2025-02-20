package com.syndicate.ptkscheduleapp.feature.schedule.presentation.theme

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.defaultShimmerTheme

val ErrorMessageColor = Color(0xFFFE5656)

val ShimmerCardTheme = defaultShimmerTheme.copy(
    blendMode = BlendMode.Overlay,
    shaderColors = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.9f)
    )
)