package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getStringByMonth
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.util.syncPanel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
internal fun WeekPanel(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedDateProvider: () -> LocalDate,
    weeks: MutableState<List<List<LocalDate>>>,
    monthValue: MutableState<Month>,
    pagerWeekStateSaved: MutableState<Int>,
    monthText: MutableState<String>,
    yearText: MutableState<Int>,
    weekPanelPageSize: MutableState<IntSize>,
    onChangeDate: (LocalDate) -> Unit = { }
) {

    LaunchedEffect(Unit) { pagerState.scrollToPage(syncPanel(weeks.value, selectedDateProvider())) }

    LaunchedEffect(pagerState, selectedDateProvider()) {
        snapshotFlow { pagerState.currentPage }.collect { page ->

            pagerWeekStateSaved.value = page

            val weekDates = weeks.value[page]

            if (selectedDateProvider() !in weekDates) {
                monthText.value = getStringByMonth(weekDates[3].month)
                monthValue.value = weekDates[3].month
                yearText.value = weekDates[3].year
            } else {
                monthText.value = getStringByMonth(selectedDateProvider().month)
                monthValue.value = selectedDateProvider().month
                yearText.value = selectedDateProvider().year
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .onSizeChanged { weekPanelPageSize.value = it },
            state = pagerState
        ) { page ->

            val weekDates = weeks.value[page]

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weekDates.forEach { date ->
                    DayItem(
                        selected = date == selectedDateProvider(),
                        value = date,
                        onChangeDate = onChangeDate
                    )
                }
            }
        }
    }
}