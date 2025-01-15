package com.syndicate.ptkscheduleapp.feature.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.feature.splash.presentation.SplashScreenContent

@Preview(showBackground = true)
@Composable
private fun PreviewSplashScreen() {
    AppTheme {
        SplashScreenContent(
            modifier = Modifier
                .fillMaxSize(),
            onLaunchApp = {}
        )
    }
}