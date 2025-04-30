package com.syndicate.ptkscheduleapp.presentation.util

import android.content.Context
import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.presentation.util.extension.findActivity

@Composable
internal actual fun changeSystemBarsColor(currentThemeMode: ThemeMode) {
    val context = LocalContext.current
    setupSystemBars(context, currentThemeMode)
}

private fun setupSystemBars(
    context: Context,
    currentThemeMode: ThemeMode
) {

    context.findActivity()?.enableEdgeToEdge(
        statusBarStyle = when {
            currentThemeMode == ThemeMode.LIGHT -> SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
            else -> SystemBarStyle.dark(scrim = Color.TRANSPARENT)
        },
        navigationBarStyle = when {
            currentThemeMode == ThemeMode.LIGHT -> SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
            else -> SystemBarStyle.dark(
                scrim = Color.TRANSPARENT
            )
        }
    )
}