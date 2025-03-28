package com.syndicate.ptkscheduleapp.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DialogPopup(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {

    var showAnimatedDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        if (showDialog) showAnimatedDialog = true
    }

    if (showAnimatedDialog) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            var animateIn by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) { animateIn = true }

            DialogScrim(
                visible = animateIn && showDialog,
                color = Color.Black.copy(alpha = 0.42f),
                onDismissRequest = onDismissRequest
            )

            AnimatedVisibility(
                visible = animateIn && showDialog,
                enter = fadeIn(spring(stiffness = Spring.StiffnessMedium))
                        + slideInVertically { -it / 4 },
                exit = slideOutVertically { it / 4 } + fadeOut()
            ) {

                Box(
                    Modifier
                        .pointerInput(Unit) { detectTapGestures { } }
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .heightIn(max = 400.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }

                DisposableEffect(Unit) {
                    onDispose {
                        showAnimatedDialog = false
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogScrim(
    visible: Boolean,
    color: Color,
    onDismissRequest: () -> Unit
) {

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = TweenSpec(),
        label = "Scrim"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { onDismissRequest() } }
    ) {
        drawRect(color = color, alpha = alpha)
    }
}