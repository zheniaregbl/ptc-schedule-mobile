package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import kotlinx.datetime.LocalDate

internal sealed interface ScheduleAction {
    data class OnChangeSelectedDate(val date: LocalDate) : ScheduleAction
    data class OnChangeSchedulePage(val page: Int) : ScheduleAction
    data class OnUpdateDailyWeekState(val currentDate: LocalDate) : ScheduleAction
    data class OnChangeTheme(val themeMode: ThemeMode) : ScheduleAction
    data object OnUpdateScheduleInfo : ScheduleAction
    data object NavigateToRoleSelection : ScheduleAction
    data object NavigateToGroupSelection : ScheduleAction
    data object NavigateToTeacherSelection : ScheduleAction
}