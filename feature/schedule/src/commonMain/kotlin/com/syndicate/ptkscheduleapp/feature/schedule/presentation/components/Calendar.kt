package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getStringByMonth
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.getWeeksFromMonth
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
internal fun Calendar(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedDate: () -> LocalDate,
    months: MutableState<List<List<LocalDate>>>,
    monthValue: MutableState<Month>,
    pagerMonthStateSaved: MutableState<Int>,
    monthText: MutableState<String>,
    yearText: MutableState<Int>,
    onChangeDate: (LocalDate) -> Unit
) {

    LaunchedEffect(Unit) {
        // TODO: Sync panels
    }

    LaunchedEffect(pagerState, selectedDate) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            pagerMonthStateSaved.value = page

            val monthDates = months.value[page]

            monthText.value = getStringByMonth(monthDates.first().month)
            monthValue.value = monthDates.first().month
            yearText.value = monthDates.first().year
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            state = pagerState
        ) { page ->
            val monthDates = months.value[page]
            val weeks = getWeeksFromMonth(monthDates)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                weeks.forEach { week ->

                    WeekRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        week = week,
                        selectedDate = selectedDate(),
                        onChangeDate = onChangeDate
                    )
                }
            }
        }
    }
}