package com.syndicate.ptkscheduleapp.feature.schedule.util

import com.syndicate.ptkscheduleapp.core.extension.plus
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

internal fun getWeeksFromMonth(month: List<LocalDate>): List<List<LocalDate>> {
    var currentDayOfWeek = DayOfWeek.MONDAY
    var arrayDaysOfWeek = ArrayList<LocalDate>()
    val arrayWeeksOfMonth = ArrayList<List<LocalDate>>()
    var currentIndex = 0
    var monthSize = month.size

    while (monthSize != 0) {
        if (month[currentIndex].dayOfWeek == currentDayOfWeek) {
            arrayDaysOfWeek.add(month[currentIndex])

            if (currentIndex != month.size - 1)
                currentIndex++
            monthSize--
        }

        if (currentDayOfWeek == DayOfWeek.SUNDAY) {

            arrayWeeksOfMonth.add(arrayDaysOfWeek)
            arrayDaysOfWeek = ArrayList()
        }

        currentDayOfWeek = currentDayOfWeek.plus(1)
    }

    if (arrayDaysOfWeek.isNotEmpty())
        arrayWeeksOfMonth.add(arrayDaysOfWeek)

    return arrayWeeksOfMonth
}
