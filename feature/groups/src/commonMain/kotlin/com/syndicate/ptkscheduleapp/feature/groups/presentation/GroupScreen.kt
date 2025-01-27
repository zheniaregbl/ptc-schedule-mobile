package com.syndicate.ptkscheduleapp.feature.groups.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.core.presentation.components.CountdownSnackbar
import com.syndicate.ptkscheduleapp.feature.groups.presentation.components.CourseSection
import com.syndicate.ptkscheduleapp.feature.groups.presentation.components.GroupSection
import com.syndicate.ptkscheduleapp.feature.groups.presentation.components.rememberPickerState
import com.syndicate.ptkscheduleapp.feature.groups.resources.Res
import com.syndicate.ptkscheduleapp.feature.groups.resources.back_svg
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.AnimatedButton
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.ZephyrButtonColor
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

class GroupScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val scheduleScreen = rememberScreen(SharedScreen.ScheduleScreen)

        val viewModel = koinViewModel<GroupViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        GroupScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            onAction = { action ->
                viewModel.onAction(action)
                when (action) {
                    is GroupAction.OnSelectGroup -> navigator.replace(scheduleScreen)
                    else -> Unit
                }
            }
        )
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun GroupScreenContent(
    modifier: Modifier = Modifier,
    state: GroupState,
    onAction: (GroupAction) -> Unit
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val groupPickerState = rememberPickerState()

    LaunchedEffect(state.toUiState()) {
        if (state.toUiState() is GroupScreenState.Error) {

            scope.launch {

                val result = snackbarHostState.showSnackbar(
                    message = state.errorMessage!!,
                    actionLabel = "Повторить",
                    duration = SnackbarDuration.Indefinite
                )

                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        if (pagerState.currentPage != 0)
                            onAction(GroupAction.GetGroupList)
                    }
                    SnackbarResult.Dismissed -> {
                        onAction(GroupAction.HideErrorMessage)
                        pagerState.animateScrollToPage(
                            page = 0,
                            animationSpec = tween(400)
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.targetPage }.collect { page ->
            if (page == 0)
                snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    BackHandler(enabled = pagerState.currentPage > 0) {
        scope.launch {
            pagerState.animateScrollToPage(
                page = 0,
                animationSpec = tween(400)
            )
        }
    }

    Box(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
        ) {

            AnimatedVisibility(
                visible = pagerState.currentPage > 0,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {

                Image(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage != 0)
                                        pagerState.animateScrollToPage(
                                            page = pagerState.currentPage - 1,
                                            animationSpec = tween(400)
                                        )
                                }
                            }
                        ),
                    painter = painterResource(Res.drawable.back_svg),
                    contentDescription = null
                )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false
        ) { page ->

            when (page) {

                0 -> {
                    CourseSection(
                        modifier = Modifier.fillMaxSize(),
                        courseProvider = { state.selectedCourseIndex },
                        onCourseClick = { courseIndex ->
                            onAction(GroupAction.OnChangeCourse(courseIndex))
                        }
                    )
                }

                1 -> {
                    GroupSection(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        groupPickerState = groupPickerState
                    )
                }
            }
        }

        AnimatedButton(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 40.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            text = "Далее",
            colors = ZephyrButtonColor().copy(
                inactiveColor = Color(0xFF4B71FF),
                pressedColor = Color(0xFF95ACFF)
            ),
            onClick = {

                if (!pagerState.isScrollInProgress) {

                    when {
                        pagerState.currentPage == 0 -> onAction(GroupAction.GetGroupList)
                        else -> onAction(GroupAction.OnSelectGroup(groupPickerState.selectedItem))
                    }

                    scope.launch {
                        if (pagerState.currentPage != pagerState.pageCount - 1)
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(400)
                            )
                    }
                }
            }
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 90.dp)
        ) { data ->
            CountdownSnackbar(
                snackbarData = data,
                shape = RoundedCornerShape(8.dp),
                containerColor = Color(0xFFFE5656),
                actionColor = Color.White
            )
        }
    }
}