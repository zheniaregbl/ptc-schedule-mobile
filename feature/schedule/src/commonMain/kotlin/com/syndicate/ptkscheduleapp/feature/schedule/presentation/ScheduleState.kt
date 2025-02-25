package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ReplacementItem
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

internal sealed class ScheduleScreenState {
    data object Idle : ScheduleScreenState()
    data object Loading : ScheduleScreenState()
    data class Success(
        val schedule: List<List<PairItem>>,
        val replacement: List<ReplacementItem>,
        val isUpperWeek: Boolean
    ) : ScheduleScreenState()
}

@Composable
internal fun ScheduleScreenState.DisplayResult(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    transitionSpec: AnimatedContentTransitionScope<ScheduleScreenState>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 200)) togetherWith
                ExitTransition.None
    },
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (ScheduleScreenState.Success) -> Unit,
) {

    AnimatedContent(
        targetState = this,
        transitionSpec = transitionSpec
    ) { state ->

        Box(
            modifier = modifier,
            contentAlignment = contentAlignment
        ) {

            when (state) {

                ScheduleScreenState.Idle -> onIdle?.invoke()

                ScheduleScreenState.Loading -> onLoading.invoke()

                is ScheduleScreenState.Success -> onSuccess.invoke(state)
            }
        }
    }
}

internal data class ScheduleState(
    val isLoading: Boolean = false,
    val isUpperWeek: Boolean? = null,
    val currentGroupNumber: String = "",
    val schedule: List<List<PairItem>> = emptyList(),
    val replacement: List<ReplacementItem> = emptyList(),
    val selectedDate: LocalDate = Clock.System.nowDate(),
    val selectedSchedulePage: Int = 0,
    val selectedDateWeekType: Boolean = false,
    val isConnected: Boolean = true
) {

    fun toUiState(): ScheduleScreenState {
        return when {
            isLoading -> ScheduleScreenState.Loading
            schedule.isNotEmpty() -> ScheduleScreenState.Success(
                schedule, replacement, isUpperWeek ?: false
            )
            else -> ScheduleScreenState.Idle
        }
    }
}