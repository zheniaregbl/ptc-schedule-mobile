package com.syndicate.ptkscheduleapp.feature.schedule.presentation.util

import kotlinx.datetime.LocalDate

internal fun syncPanel(
    content: List<List<LocalDate>>,
    selectedDate: LocalDate
): Int {

    for (i in content.indices) {
        if (content[i].indexOf(selectedDate) != -1)
            return i
    }

    return 0
}