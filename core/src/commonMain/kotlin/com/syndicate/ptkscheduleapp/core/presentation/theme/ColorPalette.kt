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
    val secondaryColor: Color,
    val thirdlyColor: Color,
    val otherColor: Color,
    val themeMode: ThemeMode
)

val LightColorPalette = ColorPalette(
    backgroundColor = FirstThemeBackground,
    contentColor = Color.Black,
    secondaryColor = Color.Black.copy(alpha = 0.1f),
    thirdlyColor = SecondThemeBackground,
    otherColor = Color(0xFF7A7979),
    themeMode = ThemeMode.LIGHT
)

val GrayColorPalette = ColorPalette(
    backgroundColor = ThirdThemeBackground,
    contentColor = Color.White,
    secondaryColor = GrayThirdTheme.copy(alpha = 0.3f),
    thirdlyColor = Color(0xFF2e2d2d),
    otherColor = Color(0xFFb5b3b3),
    themeMode = ThemeMode.GRAY
)

val DarkColorPalette = ColorPalette(
    backgroundColor = FourthThemeBackground,
    contentColor = Color.White,
    secondaryColor = ThirdThemeBackground,
    thirdlyColor = Color(0xFF151515),
    otherColor = SecondThemeBackground,
    themeMode = ThemeMode.DARK
)

val LocalColorPalette = staticCompositionLocalOf { LightColorPalette }

val MaterialTheme.colorPalette: ColorPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalColorPalette.current