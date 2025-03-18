package com.syndicate.ptkscheduleapp.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class ThemeMode {
    LIGHT,
    GRAY,
    DARK
}

data class ColorPalette(
    val backgroundColor: Color,
    val contentColor: Color,
    val otherColor: Color,
    val themeMode: ThemeMode
)

val LightColorPalette = ColorPalette(
    backgroundColor = FirstThemeBackground,
    contentColor = Color.Black,
    otherColor = SecondThemeBackground,
    themeMode = ThemeMode.LIGHT
)

val GrayColorPalette = ColorPalette(
    backgroundColor = ThirdThemeBackground,
    contentColor = Color.White,
    otherColor = GrayThirdTheme,
    themeMode = ThemeMode.GRAY
)

val DarkColorPalette = ColorPalette(
    backgroundColor = FourthThemeBackground,
    contentColor = Color.White,
    otherColor = ThirdThemeBackground,
    themeMode = ThemeMode.DARK
)

val LocalColorPalette = staticCompositionLocalOf { LightColorPalette }

val MaterialTheme.colorPalette: ColorPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalColorPalette.current