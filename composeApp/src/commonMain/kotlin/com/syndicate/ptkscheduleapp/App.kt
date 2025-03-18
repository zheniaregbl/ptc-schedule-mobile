package com.syndicate.ptkscheduleapp

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.FadeTransition
import com.syndicate.ptkscheduleapp.core.presentation.ThemeViewModel
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.LocalColorPalette
import com.syndicate.ptkscheduleapp.feature.splash.presentation.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalVoyagerApi::class)
@Composable
@Preview
fun App() {

    ProvideNavigatorLifecycleKMPSupport {

        val themeViewModel = koinViewModel<ThemeViewModel>()
        val themeState by themeViewModel.state.collectAsState()

        AppTheme(themeMode = themeState.themeMode) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalColorPalette.current.backgroundColor)
            ) {

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