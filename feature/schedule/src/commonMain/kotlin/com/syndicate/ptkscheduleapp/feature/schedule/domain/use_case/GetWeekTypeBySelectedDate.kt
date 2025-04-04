package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil

class GetWeekTypeBySelectedDate() {
    operator fun invoke(
        responseWeekType: Boolean,
        currentWeekNumber: Int,
        currentSchedulePage: Int
    ): Boolean {
        val startWeekType = ScheduleUtil
            .getCurrentTypeWeek(responseWeekType, currentWeekNumber, 0)
        return if (currentSchedulePage / 7 % 2 == 0)
            startWeekType else !startWeekType
    }
}