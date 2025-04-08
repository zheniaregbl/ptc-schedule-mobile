package com.syndicate.ptkscheduleapp.feature.teacher.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.core.presentation.components.CircleLoading
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.components.SearchBar

internal class TeacherScreen : Screen {

    @Composable
    override fun Content() {

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
                        "Преподаватель 17"
                    )
                )
            )
        }

        TeacherScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            onAction = { }
        )
    }
}

@Composable
internal fun TeacherScreenContent(
    modifier: Modifier = Modifier,
    state: State<TeacherListState>,
    onAction: (TeacherListAction) -> Unit
) {

    val currentThemeMode = MaterialTheme.colorPalette.themeMode
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Поиск преподавателя",
            style = LocalTextStyle.current,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorPalette.contentColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            onImeSearch = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        state.value.toUiState().DisplayResult(
            modifier = Modifier.fillMaxSize(),
            onLoading = {
                CircleLoading(
                    size = 60.dp,
                    color = if (currentThemeMode == ThemeMode.LIGHT) Color(0xFF4B71FF)
                    else Color.White
                )
            },
            onSuccess = { screenState ->

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    item { Spacer(modifier = Modifier.height(2.dp)) }

                    itemsIndexed(screenState.teacherList) { index, teacher ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { onAction(TeacherListAction.OnSelectTeacherList(teacher)) }
                                ),
                            verticalArrangement = Arrangement.spacedBy(18.dp)
                        ) {

                            Text(
                                text = teacher,
                                style = LocalTextStyle.current,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorPalette.contentColor
                            )

                            if (index != screenState.teacherList.lastIndex)
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                        .height(1.dp)
                                        .background(MaterialTheme.colorPalette.contentColor.copy(0.2f))
                                )
                        }
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(
                                    bottom = WindowInsets
                                        .navigationBars
                                        .asPaddingValues()
                                        .calculateBottomPadding()
                                )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.0f to MaterialTheme.colorPalette.backgroundColor,
                                0.05f to Color.Transparent
                            )
                        )
                )
            },
            onError = { }
        )
    }
}