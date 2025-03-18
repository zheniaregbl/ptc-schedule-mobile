package com.syndicate.ptkscheduleapp.core.presentation

import androidx.compose.ui.geometry.Offset
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode

sealed interface ThemeAction {
    data class ChangeTheme(val themeMode: ThemeMode, val offset: Offset) : ThemeAction
    data object LockChangeTheme : ThemeAction
    data object UnlockChangeTheme : ThemeAction
}