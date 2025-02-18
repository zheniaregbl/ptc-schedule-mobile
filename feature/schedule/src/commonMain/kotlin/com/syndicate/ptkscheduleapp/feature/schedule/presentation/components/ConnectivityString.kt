package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.core.presentation.theme.ConnectionGreen
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightRed
import com.syndicate.ptkscheduleapp.feature.schedule.createConnectivityState
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleAction
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.delay

@Composable
internal fun ConnectivityString(onAction: (ScheduleAction) -> Unit) {

    val connectivityState = createConnectivityState(true)
    var showString by remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = when (connectivityState.status) {
            Connectivity.Status.Disconnected -> LightRed
            is Connectivity.Status.Connected -> ConnectionGreen
            null -> Color.White
        },
        animationSpec = tween(
            delayMillis = 400,
            easing = Ease
        ),
        label = "Connection string color"
    )
    var connectionInfo by remember { mutableStateOf("Соединение разорвано") }

    LaunchedEffect(connectivityState.status) {

        when (connectivityState.status) {

            is Connectivity.Status.Connected -> {
                onAction(ScheduleAction.UpdateScheduleInfo)
                connectionInfo = "Соединение восстановлено"
                delay(1200)
                showString = false
            }

            Connectivity.Status.Disconnected -> {
                connectionInfo = "Соединение разорвано"
                showString = true
            }

            null -> { showString = false }
        }
    }

    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = showString
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp),
            contentAlignment = Alignment.Center
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    color = color,
                    size = size
                )
            }

            Text(
                text = connectionInfo,
                style = LocalTextStyle.current,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}