package com.syndicate.ptkscheduleapp.feature.splash.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.syndicate.ptkscheduleapp.core.presentation.theme.MainBlue
import com.syndicate.ptkscheduleapp.feature.splash.resources.Res
import com.syndicate.ptkscheduleapp.navigation.SharedScreen
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi

class SplashScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val selectCourseScreen = rememberScreen(SharedScreen.SelectCourse)

        SplashScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            onLaunchApp = { navigator.replace(selectCourseScreen) }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onLaunchApp: () -> Unit
) {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/logo_lottie_blue.json").decodeToString()
        )
    }
    var isShowText by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1,
        speed = 0.85f
    )

    LaunchedEffect(progress) {
        if (progress == 1f) {
            isPlaying = false
            isShowText = true
            delay(1000L)
            onLaunchApp()
        }
    }

    Box(modifier = modifier) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(166.dp),
                painter = rememberLottiePainter(
                    composition = composition,
                    progress = { progress }
                ),
                contentDescription = null
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            visible = isShowText,
            enter = fadeIn(tween(500))
        ) {
            Text(
                text = "by Syndicate",
                style = LocalTextStyle.current,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MainBlue
            )
        }
    }
}