package com.syndicate.ptkscheduleapp.feature.groups.presentation

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

internal sealed class GroupScreenState {
    data object Idle : GroupScreenState()
    data object Loading : GroupScreenState()
    data class Error(val errorMessage: String) : GroupScreenState()
    data class Success(val groupList: List<String>) : GroupScreenState()
}

@Composable
internal fun GroupScreenState.DisplayResult(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    transitionSpec: AnimatedContentTransitionScope<GroupScreenState>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 200)) togetherWith
                ExitTransition.None
    },
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (GroupScreenState.Success) -> Unit,
    onError: @Composable (GroupScreenState.Error) -> Unit
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

                is GroupScreenState.Error -> onError.invoke(state)

                GroupScreenState.Idle -> onIdle?.invoke()

                GroupScreenState.Loading -> onLoading.invoke()

                is GroupScreenState.Success -> onSuccess.invoke(state)
            }
        }
    }
}

internal data class GroupState(
    val isLoading: Boolean = false,
    val groupList: List<String> = listOf("-", "-", "-", "-", "-", "-"),
    val selectedCourseIndex: Int = 0,
    val errorMessage: String? = null
) {

    fun toUiState(): GroupScreenState {
        return if (isLoading) GroupScreenState.Loading
        else GroupScreenState.Success(groupList)
    }
}