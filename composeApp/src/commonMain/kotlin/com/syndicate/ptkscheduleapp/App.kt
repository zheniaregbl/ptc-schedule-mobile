package com.syndicate.ptkscheduleapp

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.FadeTransition
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.feature.splash.presentation.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalVoyagerApi::class)
@Composable
@Preview
fun App() {

    AppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            ProvideNavigatorLifecycleKMPSupport {

                Navigator(
                    screen = SplashScreen(),
                    disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
                ) { navigator ->

                    FadeTransition(
                        navigator = navigator,
                        disposeScreenAfterTransitionEnd = true,
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
}