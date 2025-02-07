package com.syndicate.ptkscheduleapp.core.presentation.util.extension

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

fun Modifier.scaleOnClick(
    scaleFactor: Float = 0.95f,
    durationMillis: Int = 100,
    easing: Easing = EaseOut
): Modifier = composed {

    val scope = rememberCoroutineScope()

    val scale = remember { Animatable(1f) }
    var isPressed by remember { mutableStateOf(false) }

    Modifier
        .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()

                    scope.launch {
                        when (event.type) {
                            PointerEventType.Press -> {
                                isPressed = true
                                scale.stop()
                                scale.animateTo(
                                    scaleFactor,
                                    animationSpec = tween(
                                        durationMillis = durationMillis,
                                        easing = easing
                                    )
                                )
                            }
                            PointerEventType.Release -> {
                                isPressed = false
                                scale.stop()
                                scale.animateTo(
                                    1f,
                                    animationSpec = tween(
                                        durationMillis = durationMillis,
                                        easing = easing
                                    )
                                )
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
}