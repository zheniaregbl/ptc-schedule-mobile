package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import kotlinx.datetime.LocalDate

internal sealed interface ScheduleAction {
    data class ChangeSelectedDate(val date: LocalDate) : ScheduleAction
    data class ChangeSchedulePage(val page: Int) : ScheduleAction
    data class UpdateDailyWeekState(val currentDate: LocalDate) : ScheduleAction
    data object UpdateScheduleInfo : ScheduleAction
}