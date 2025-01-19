package com.syndicate.ptkscheduleapp.feature.schedule.util

import com.syndicate.ptkscheduleapp.core.extension.plus
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus

internal fun getMonthsFromWeeks(weeks: List<List<LocalDate>>): List<List<LocalDate>> {
    var currentMonth = Month.JANUARY
    var arrayDaysOfMonth = ArrayList<LocalDate>()
    val arrayOfMonths = ArrayList<List<LocalDate>>()

    for (i in weeks.indices) {

        for (j in weeks[i].indices) {

            if (weeks[i][j].month == currentMonth)
                arrayDaysOfMonth.add(weeks[i][j])
            else {
                if (arrayDaysOfMonth.size in 28..31) {

                    arrayOfMonths.add(arrayDaysOfMonth)

                    arrayDaysOfMonth = ArrayList()

                    currentMonth = currentMonth.plus(1)

                    arrayDaysOfMonth.add(weeks[i][j])
                }
            }
        }
    }

    if (arrayDaysOfMonth.size != 0) {

        var day = arrayDaysOfMonth.last().plus(1, DateTimeUnit.DAY)

        while (day.month == currentMonth) {

            arrayDaysOfMonth.add(day)
            day = day.plus(1, DateTimeUnit.DAY)
        }
    }

    arrayOfMonths.add(arrayDaysOfMonth)

    return arrayOfMonths
}