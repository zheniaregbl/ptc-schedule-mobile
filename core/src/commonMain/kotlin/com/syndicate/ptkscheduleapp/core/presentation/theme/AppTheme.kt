package com.syndicate.ptkscheduleapp.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = MontserratTypography(),
        content = content
    )
}