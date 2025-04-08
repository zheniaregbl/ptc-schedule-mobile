package com.syndicate.ptkscheduleapp.feature.teacher.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherListState
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherScreenContent

@Preview
@Composable
private fun TeacherScreenLightPreview() {

    val state = remember {
        mutableStateOf(
            TeacherListState(
                teacherList = listOf(
                    "Преподаватель 1",
                    "Преподаватель 2",
                    "Преподаватель 3",
                    "Преподаватель 4",
                    "Преподаватель 5",
                    "Преподаватель 6",
                    "Преподаватель 7",
                    "Преподаватель 8",
                    "Преподаватель 9",
                    "Преподаватель 10",
                    "Преподаватель 11",
                    "Преподаватель 12",
                    "Преподаватель 13",
                    "Преподаватель 14",
                    "Преподаватель 15",
                    "Преподаватель 16",
                    "Преподаватель 17",
                )
            )
        )
    }

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorPalette.backgroundColor)
        ) {
            TeacherScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun TeacherScreenGrayPreview() {

    val state = remember {
        mutableStateOf(
            TeacherListState(
                teacherList = listOf(
                    "Преподаватель 1",
                    "Преподаватель 2",
                    "Преподаватель 3",
                    "Преподаватель 4",
                    "Преподаватель 5",
                    "Преподаватель 6",
                    "Преподаватель 7",
                    "Преподаватель 8",
                    "Преподаватель 9",
                    "Преподаватель 10",
                    "Преподаватель 11",
                    "Преподаватель 12",
                    "Преподаватель 13",
                    "Преподаватель 14",
                    "Преподаватель 15",
                    "Преподаватель 16",
                    "Преподаватель 17",
                )
            )
        )
    }

    AppTheme(themeMode = ThemeMode.GRAY) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorPalette.backgroundColor)
        ) {
            TeacherScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun TeacherScreenDarkPreview() {

    val state = remember {
        mutableStateOf(
            TeacherListState(
                teacherList = listOf(
                    "Преподаватель 1",
                    "Преподаватель 2",
                    "Преподаватель 3",
                    "Преподаватель 4",
                    "Преподаватель 5",
                    "Преподаватель 6",
                    "Преподаватель 7",
                    "Преподаватель 8",
                    "Преподаватель 9",
                    "Преподаватель 10",
                    "Преподаватель 11",
                    "Преподаватель 12",
                    "Преподаватель 13",
                    "Преподаватель 14",
                    "Преподаватель 15",
                    "Преподаватель 16",
                    "Преподаватель 17",
                )
            )
        )
    }

    AppTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorPalette.backgroundColor)
        ) {
            TeacherScreenContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onAction = {}
            )
        }
    }
}