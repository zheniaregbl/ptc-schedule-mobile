package com.syndicate.ptkscheduleapp.feature.teacher.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.core.presentation.components.CircleLoading
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.components.SearchBar
import com.syndicate.ptkscheduleapp.feature.teacher.resources.Res
import com.syndicate.ptkscheduleapp.feature.teacher.resources.error_svg
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

internal class TeacherScreen : Screen {

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val scheduleScreen = rememberScreen(SharedScreen.ScheduleScreen)

        val viewModel = koinViewModel<TeacherListViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(state.value.selectedTeacher) {
            if (state.value.selectedTeacher != null) {
                navigator.dispose(scheduleScreen)
                navigator.replaceAll(scheduleScreen)
            }
        }

        TeacherScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            onAction = { action -> viewModel.onAction(action) }
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
            value = state.value.searchTeacherText,
            onValueChange = { onAction(TeacherListAction.OnSearchTeacherChange(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        state.value.toUiState().DisplayResult(
            modifier = Modifier.fillMaxSize(),
            onLoading = {
                CircleLoading(
                    modifier = Modifier
                        .padding(bottom = 50.dp),
                    size = 60.dp,
                    color = if (currentThemeMode == ThemeMode.LIGHT) Color(0xFF4B71FF)
                    else Color.White
                )
            },
            onSuccess = { screenState ->

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {

                    item { Spacer(modifier = Modifier.height(2.dp)) }

                    itemsIndexed(screenState.teacherList) { index, teacher ->

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(9.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .fillMaxWidth()
                                    .clickable { onAction(TeacherListAction.OnSelectTeacherList(teacher)) }
                                    .padding(horizontal = 6.dp)
                                    .padding(vertical = 9.dp)
                            ) {
                                Text(
                                    text = teacher,
                                    style = LocalTextStyle.current,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorPalette.contentColor
                                )
                            }

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
            onError = { screenState ->

                Column(
                    modifier = Modifier.padding(bottom = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Image(
                        painter = painterResource(Res.drawable.error_svg),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorPalette.contentColor)
                    )

                    Text(
                        text = screenState.errorMessage,
                        style = LocalTextStyle.current,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorPalette.contentColor
                    )
                }
            }
        )
    }
}