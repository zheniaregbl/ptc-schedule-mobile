package com.syndicate.ptkscheduleapp

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.feature.splash.presentation.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    AppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Navigator(SplashScreen()) { navigator ->

                FadeTransition(
                    navigator = navigator,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = Ease,
                        delayMillis = 100
                    )
                )
            }
        }
    }
}