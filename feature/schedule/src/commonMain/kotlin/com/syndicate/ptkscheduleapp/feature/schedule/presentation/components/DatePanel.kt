package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.components.AutoResizeText
import com.syndicate.ptkscheduleapp.core.presentation.components.FontSizeRange
import com.syndicate.ptkscheduleapp.core.presentation.theme.FirstThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.SelectedBlue
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.feature.schedule.resources.Res
import com.syndicate.ptkscheduleapp.feature.schedule.resources.calendar_svg
import com.syndicate.ptkscheduleapp.feature.schedule.resources.expand_arrow_svg
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getCurrentMonth
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getMonthsFromWeeks
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getStringByMonth
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource

internal enum class PanelState {
    WeekPanel,
    CalendarPanel
}

@Composable
internal fun DatePanel(
    modifier: Modifier = Modifier,
    panelState: MutableState<PanelState> = mutableStateOf(PanelState.WeekPanel),
    weekPanelPagerState: PagerState,
    state: State<ScheduleState>,
    weeks: MutableState<List<List<LocalDate>>>,
    pagerWeekStateSaved: MutableState<Int> = mutableIntStateOf(0),
    weekPanelPageSize: MutableState<IntSize>,
    onChangeDate: (LocalDate) -> Unit = { },
    onHideCalendar: () -> Unit = { }
) {

    val currentLocalDate = Clock.System.nowDate()

    val months = remember { mutableStateOf(getMonthsFromWeeks(weeks.value)) }

    val pagerMonthStateSaved = remember {
        mutableIntStateOf(getCurrentMonth(months.value, currentLocalDate))
    }
    val calendarPagerState = rememberPagerState(
        initialPage = pagerMonthStateSaved.value,
        initialPageOffsetFraction = 0f,
        pageCount = { months.value.size }
    )

    val monthValue = remember { mutableStateOf(currentLocalDate.month) }
    val monthText = remember {
        mutableStateOf(
            getStringByMonth(state.value.selectedDate.month)
        )
    }
    val yearText = remember {
        mutableIntStateOf(
            state.value.selectedDate.year
        )
    }

    val colorBorder = Color.Black.copy(alpha = .3f)

    Box(modifier = modifier) {

        AnimatedVisibility(
            visible = panelState.value == PanelState.CalendarPanel,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.32f))
                    .pointerInput(onHideCalendar) { detectTapGestures { onHideCalendar() } }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 25.dp,
                        bottomEnd = 25.dp
                    )
                )
                .background(FirstThemeBackground)
                .drawBehind {

                    drawLine(
                        color = colorBorder,
                        start = Offset(x = 25.dp.toPx(), y = size.height),
                        end = Offset(x = size.width - 25.dp.toPx(), y = size.height),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawLine(
                        color = colorBorder,
                        start = Offset(x = 0.dp.toPx(), y = 0.dp.toPx()),
                        end = Offset(x = 0.dp.toPx(), y = size.height - 25.dp.toPx()),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawLine(
                        color = colorBorder,
                        start = Offset(x = size.width, y = 0.dp.toPx()),
                        end = Offset(x = size.width, y = size.height - 25.dp.toPx()),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawArc(
                        color = colorBorder,
                        startAngle = 180f,
                        sweepAngle = -90f,
                        useCenter = false,
                        topLeft = Offset(x = 0f, y = size.height - 25.dp.toPx() * 2),
                        size = Size(25.dp.toPx() * 2, 25.dp.toPx() * 2),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    drawArc(
                        color = colorBorder,
                        startAngle = 360f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(
                            x = size.width - 25.dp.toPx() * 2,
                            y = size.height - 25.dp.toPx() * 2
                        ),
                        size = Size(25.dp.toPx() * 2, 25.dp.toPx() * 2),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (panelState.value == PanelState.CalendarPanel)
                        panelState.value = PanelState.CalendarPanel
                }
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 35.dp,
                            end = 25.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = if (state.value.selectedDateWeekType) "Верхняя неделя"
                        else "Нижняя неделя",
                        style = LocalTextStyle.current,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Text(
                        text = "Группа: ${state.value.currentGroupNumber}",
                        style = LocalTextStyle.current,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 35.dp,
                            end = 25.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${monthText.value}, ${yearText.intValue}",
                        style = LocalTextStyle.current,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        DateButton(
                            selectedDateProvider = { state.value.selectedDate },
                            onClick = { onChangeDate(currentLocalDate) }
                        )

                        ExpandedButton(
                            expanded = panelState.value == PanelState.WeekPanel,
                            onClick = {
                                panelState.value = when (panelState.value) {
                                    PanelState.WeekPanel -> PanelState.CalendarPanel
                                    PanelState.CalendarPanel -> PanelState.WeekPanel
                                }
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС").forEach {

                        Box(
                            modifier = Modifier
                                .width(36.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            AutoResizeText(
                                text = it,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                maxLines = 1,
                                fontSizeRange = FontSizeRange(
                                    min = 12.sp,
                                    max = 15.sp
                                )
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = panelState.value == PanelState.WeekPanel) {

                    WeekPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 6.dp,
                                bottom = 12.dp
                            ),
                        pagerState = weekPanelPagerState,
                        selectedDateProvider = { state.value.selectedDate },
                        weeks = weeks,
                        monthValue = monthValue,
                        pagerWeekStateSaved = pagerWeekStateSaved,
                        monthText = monthText,
                        yearText = yearText,
                        weekPanelPageSize,
                        onChangeDate = onChangeDate
                    )
                }

                AnimatedVisibility(visible = panelState.value == PanelState.CalendarPanel) {

                    Box(
                        modifier = Modifier
                            .padding(
                                top = 6.dp
                            )
                    ) {

                        Calendar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = 12.dp
                                ),
                            pagerState = calendarPagerState,
                            selectedDate = { state.value.selectedDate },
                            months = months,
                            monthValue = monthValue,
                            pagerMonthStateSaved = pagerMonthStateSaved,
                            monthText = monthText,
                            yearText = yearText,
                            onChangeDate = { date ->
                                onChangeDate(date)
                                onHideCalendar()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandedButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.rotate(if (expanded) 180f else 0f),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.size(26.dp),
            painter = painterResource(Res.drawable.expand_arrow_svg),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
fun DateButton(
    selectedDateProvider: () -> LocalDate,
    onClick: () -> Unit
) {

    val currentLocalDate = Clock.System.nowDate()

    AnimatedVisibility(
        visible = selectedDateProvider() != currentLocalDate,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        IconButton(onClick = onClick) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(Res.drawable.calendar_svg),
                contentDescription = null,
                colorFilter = ColorFilter.tint(SelectedBlue)
            )
        }
    }
}