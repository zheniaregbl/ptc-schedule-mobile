package com.syndicate.ptkscheduleapp.core.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val themeChangeSpec = tween<Color>(
    durationMillis = 260,
    easing = EaseIn
)

@Composable
fun AppTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {

    val targetColorPalette = when (themeMode) {
        ThemeMode.LIGHT -> LightColorPalette
        ThemeMode.GRAY -> GrayColorPalette
        ThemeMode.DARK -> DarkColorPalette
    }

    val animatedColorPalette = ColorPalette(
        backgroundColor = animateColorAsState(
            targetValue = targetColorPalette.backgroundColor,
            animationSpec = themeChangeSpec
        ).value,
        contentColor = animateColorAsState(
            targetValue = targetColorPalette.contentColor,
            animationSpec = themeChangeSpec
        ).value,
        secondaryColor = animateColorAsState(
            targetValue = targetColorPalette.secondaryColor,
            animationSpec = themeChangeSpec
        ).value,
        thirdlyColor = animateColorAsState(
            targetValue = targetColorPalette.thirdlyColor,
            animationSpec = themeChangeSpec
        ).value,
        otherColor = animateColorAsState(
            targetValue = targetColorPalette.otherColor,
            animationSpec = themeChangeSpec
        ).value,
        themeMode = targetColorPalette.themeMode,
    )

    CompositionLocalProvider(LocalColorPalette provides animatedColorPalette) {
        MaterialTheme(
            colorScheme = lightColorScheme(),
            typography = MontserratTypography(),
            content = content
        )
    }
}