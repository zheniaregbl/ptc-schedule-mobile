package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.core.extension.plus
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
internal fun WeekRow(
    modifier: Modifier = Modifier,
    week: List<LocalDate>,
    selectedDate: LocalDate,
    onChangeDate: (LocalDate) -> Unit
) {

    var currentDayOfWeek = DayOfWeek.MONDAY
    var currentIndex = 0
    var counterDays = 7

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        while (counterDays != 0) {

            if (week[currentIndex].dayOfWeek == currentDayOfWeek) {

                DayItem(
                    selected = week[currentIndex] == selectedDate,
                    value = week[currentIndex],
                    onChangeDate = onChangeDate
                )

                if (currentIndex != week.size - 1)
                    currentIndex++

            } else Box(modifier = Modifier.size(36.dp))

            counterDays--
            currentDayOfWeek = currentDayOfWeek.plus(1)
        }
    }
}