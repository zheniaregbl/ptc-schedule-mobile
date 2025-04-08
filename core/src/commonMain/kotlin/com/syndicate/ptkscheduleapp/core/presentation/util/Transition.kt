package com.syndicate.ptkscheduleapp.core.presentation.util

import androidx.annotation.IntRange
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
internal fun InfiniteTransition.fractionTransition(
    initialValue: Float,
    targetValue: Float,
    @IntRange(from = 1, to = 4) fraction: Int = 1,
    durationMillis: Int,
    delayMillis: Int = 0,
    offsetMillis: Int = 0,
    repeatMode: RepeatMode = RepeatMode.Restart,
    easing: Easing = FastOutSlowInEasing
): State<Float> {
    return animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                this.durationMillis = durationMillis
                this.delayMillis = delayMillis
                initialValue at 0 using easing
                when(fraction){
                    1 ->{
                        targetValue at durationMillis using easing
                    }
                    2 ->{
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue at durationMillis using easing
                    }
                    3 ->{
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue / fraction * 2 at durationMillis / fraction * 2 using easing
                        targetValue at durationMillis using easing
                    }
                    4 ->{
                        targetValue / fraction at durationMillis / fraction using easing
                        targetValue / fraction * 2 at durationMillis / fraction * 2 using easing
                        targetValue / fraction * 3 at durationMillis / fraction * 3 using easing
                        targetValue at durationMillis using easing
                    }
                }
            },
            repeatMode,
            StartOffset(offsetMillis)
        )
    )
}
