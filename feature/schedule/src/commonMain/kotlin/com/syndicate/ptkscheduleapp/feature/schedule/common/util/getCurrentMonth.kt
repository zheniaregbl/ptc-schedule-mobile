package com.syndicate.ptkscheduleapp.feature.schedule.common.util

import kotlinx.datetime.LocalDate

internal fun getCurrentMonth(
    months: List<List<LocalDate>>,
    currentDate: LocalDate
): Int {
    for (i in months.indices)
        if (months[i][3].month == currentDate.month) return i

    return 0
}