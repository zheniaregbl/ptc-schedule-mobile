package com.syndicate.ptkscheduleapp.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {

    val colorPalette = when (themeMode) {
        ThemeMode.LIGHT -> LightColorPalette
        ThemeMode.GRAY -> GrayColorPalette
        ThemeMode.DARK -> DarkColorPalette
    }

    CompositionLocalProvider(LocalColorPalette provides colorPalette) {
        MaterialTheme(
            colorScheme = lightColorScheme(),
            typography = MontserratTypography(),
            content = content
        )
    }
}