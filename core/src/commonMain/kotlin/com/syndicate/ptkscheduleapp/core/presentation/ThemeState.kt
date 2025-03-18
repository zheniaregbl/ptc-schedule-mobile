package com.syndicate.ptkscheduleapp.core.presentation

import androidx.compose.ui.geometry.Offset
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode

data class ThemeState(
    val themeMode: ThemeMode = ThemeMode.LIGHT,
    val animationOffset: Offset = Offset(0f, 0f),
    val enableChangeTheme: Boolean = true
)