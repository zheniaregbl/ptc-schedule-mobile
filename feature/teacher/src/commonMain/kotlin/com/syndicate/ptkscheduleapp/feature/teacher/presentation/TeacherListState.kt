package com.syndicate.ptkscheduleapp.feature.teacher.presentation

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

internal sealed class TeacherListScreenState {
    data object Idle : TeacherListScreenState()
    data object Loading : TeacherListScreenState()
    data class Success(val teacherList: List<String>) : TeacherListScreenState()
    data class Error(val errorMessage: String) : TeacherListScreenState()
}

@Composable
internal fun TeacherListScreenState.DisplayResult(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    transitionSpec: AnimatedContentTransitionScope<TeacherListScreenState>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 200)) togetherWith
                ExitTransition.None
    },
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (TeacherListScreenState.Success) -> Unit,
    onError: @Composable (TeacherListScreenState.Error) -> Unit
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
                TeacherListScreenState.Idle -> onIdle?.invoke()
                TeacherListScreenState.Loading -> onLoading.invoke()
                is TeacherListScreenState.Error -> onError.invoke(state)
                is TeacherListScreenState.Success -> onSuccess.invoke(state)
            }
        }
    }
}

internal data class TeacherListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchTeacherText: String = "",
    val teacherList: List<String> = emptyList(),
    val filterTeacherList: List<String> = emptyList(),
    val selectedTeacher: String? = null
) {

    fun toUiState(): TeacherListScreenState {
        return when {
            isLoading -> TeacherListScreenState.Loading
            teacherList.isNotEmpty() -> TeacherListScreenState.Success(filterTeacherList)
            errorMessage != null -> TeacherListScreenState.Error(errorMessage)
            else -> TeacherListScreenState.Idle
        }
    }
}