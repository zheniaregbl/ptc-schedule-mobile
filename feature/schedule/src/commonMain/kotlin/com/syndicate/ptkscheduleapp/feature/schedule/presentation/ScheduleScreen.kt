package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import com.syndicate.ptkscheduleapp.core.presentation.theme.FirstThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.SecondThemeBackground
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleViewModel.Companion.weeks
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.components.DatePanel
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.components.PairCard
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.components.PanelState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus
import org.koin.compose.viewmodel.koinViewModel

internal class ScheduleScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = koinViewModel<ScheduleViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()

        ScheduleScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            initPage = initPage,
            onAction = { action -> viewModel.onAction(action) }
        )
    }

    companion object {

        private val currentDate = Clock.System.nowDate()

        private val initWeekNumber = ScheduleUtil
            .getCurrentWeek(weeks, currentDate)

        val initPage = (initWeekNumber * 7) + weeks[initWeekNumber]
            .indexOf(currentDate)
    }
}

@Composable
internal fun ScheduleScreenContent(
    modifier: Modifier = Modifier,
    state: State<ScheduleState>,
    initPage: Int,
    onAction: (ScheduleAction) -> Unit
) {

    val scope = rememberCoroutineScope()

    val panelState = remember { mutableStateOf(PanelState.WeekPanel) }

    val weeks = remember {
        mutableStateOf(
            ScheduleUtil
                .getWeeksFromStartDate(
                    LocalDate(Clock.System.nowDate().year, Month.JANUARY, 1),
                    78
                )
        )
    }
    val initWeekNumber = ScheduleUtil
        .getCurrentWeek(weeks.value, Clock.System.nowDate())

    val pagerWeekStateSaved = remember {
        mutableIntStateOf(initWeekNumber)
    }

    val weekPanelPagerState = rememberPagerState(
        initialPage = pagerWeekStateSaved.intValue,
        initialPageOffsetFraction = 0f,
        pageCount = { weeks.value.size }
    )

    val schedulePagerState = rememberPagerState(
        initialPage = initPage,
        initialPageOffsetFraction = 0f,
        pageCount = { 78 * 7 }
    )

    val weekPanelPageSize = remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(schedulePagerState) {

        // TODO: Fix bug with scrolling on schedulePagerState

        snapshotFlow { schedulePagerState.currentPage }.collect { _ ->

            val weekNumber = schedulePagerState.targetPage / 7
            val indexInWeek = schedulePagerState.targetPage % 7

            onAction(ScheduleAction.ChangeSchedulePage(schedulePagerState.targetPage))
            onAction(ScheduleAction.ChangeSelectedDate(weeks.value[weekNumber][indexInWeek]))
            onAction(ScheduleAction.UpdateDailyWeekState(weeks.value[weekNumber][indexInWeek]))
        }
    }

    LaunchedEffect(state.value.selectedSchedulePage) {

        val weekNumber = state.value.selectedSchedulePage / 7

        if (weekPanelPagerState.currentPage != weekNumber)
            scope.launch { weekPanelPagerState.animateScrollToPage(page = weekNumber) }
    }

    Box(modifier = modifier) {

        state.value.toUiState().DisplayResult(
            modifier = Modifier.fillMaxSize(),
            onIdle = {},
            onLoading = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item { Spacer(Modifier.height(160.dp)) }

                    item { Text("Loading") }
                }
            },
            onError = {},
            onSuccess = { screenState ->

                val scheduleList = listOf(
                    ScheduleUtil.getWeekScheduleByWeekType(
                        screenState.schedule,
                        true
                    ),
                    ScheduleUtil.getWeekScheduleByWeekType(
                        screenState.schedule,
                        false
                    )
                )

                val startScheduleIndex = if (
                    ScheduleUtil.getCurrentTypeWeek(
                        screenState.isUpperWeek,
                        initWeekNumber,
                        0
                    )
                ) 0 else 1

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    state = schedulePagerState
                ) { page ->

                    val pageDate = weeks.value[0][0].plus(page.toLong(), DateTimeUnit.DAY)

                    val currentScheduleIndex = if (page / 7 % 2 == 0) startScheduleIndex
                    else 1 - startScheduleIndex

                    val dailySchedule: List<PairItem> = try {
                        scheduleList[currentScheduleIndex][page % 7]
                    } catch (_: Exception) {
                        emptyList()
                    }

                    val currentSchedule = ScheduleUtil
                        .groupDailyScheduleBySubgroup(dailySchedule)

                    if (dailySchedule.isNotEmpty()) {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 16.dp
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            item { Spacer(modifier = Modifier.height(180.dp)) }

                            itemsIndexed(
                                items = currentSchedule,
                                key = { index, _ ->
                                    index
                                }
                            ) { index, pair ->

                                if (pair.size > 1) {

                                    PairCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(color = FirstThemeBackground)
                                            .border(
                                                width = 2.dp,
                                                color = Color.Black.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        pairList = pair,
                                        isDark = false
                                    )

                                } else {

                                    PairCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(color = FirstThemeBackground)
                                            .border(
                                                width = 2.dp,
                                                color = Color.Black.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        pair = pair.first(),
                                        isDark = false
                                    )
                                }

                                if (index != currentSchedule.lastIndex)
                                    Spacer(modifier = Modifier.height(14.dp))

                            }

                            item {
                                Spacer(
                                    modifier = Modifier
                                        .height(
                                            WindowInsets
                                                .systemBars
                                                .asPaddingValues()
                                                .calculateBottomPadding()
                                                    + 60.dp
                                        )
                                )
                            }
                        }

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "Нет занятий",
                                style = LocalTextStyle.current,
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        }

                    }
                }
            }
        )

        DatePanel(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            panelState = panelState,
            weekPanelPagerState = weekPanelPagerState,
            state = state,
            weeks = weeks,
            pagerWeekStateSaved = pagerWeekStateSaved,
            weekPanelPageSize = weekPanelPageSize,
            onChangeDate = { date ->

                val weekNumber = ScheduleUtil.getCurrentWeek(weeks.value, date)
                val page = (weekNumber * 7) + weeks.value[weekNumber].indexOf(date)

                scope.launch {
                    schedulePagerState.animateScrollToPage(
                        page = page,
                        animationSpec = tween()
                    )
                }
            },
            onHideCalendar = {
                if (panelState.value == PanelState.CalendarPanel) {
                    panelState.value = PanelState.WeekPanel
                }
            }
        )
    }
}