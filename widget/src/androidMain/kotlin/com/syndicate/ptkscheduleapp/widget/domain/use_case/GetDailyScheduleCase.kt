package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

internal class GetDailyScheduleCase() {

    operator fun invoke(
        userIdentifier: UserIdentifier,
        weekType: Boolean?,
        schedule: List<List<PairItem>>,
        replacement: List<ReplacementItem>
    ): List<List<PairItem>> {

        val weeks = ScheduleUtil.getWeeksFromStartDate(
            LocalDate(Clock.System.nowDate().year, Month.JANUARY, 1),
            78
        )
        val currentWeekNumber = ScheduleUtil.getCurrentWeek(
            weeks,
            Clock.System.nowDate()
        )

        val scheduleList = listOf(
            ScheduleUtil.getWeekScheduleByWeekType(schedule, true),
            ScheduleUtil.getWeekScheduleByWeekType(schedule, false)
        )
        val startScheduleIndex = if (
            ScheduleUtil.getCurrentTypeWeek(
                weekType == true, currentWeekNumber, 0
            )
        ) 0 else 1

        val dayNumber = (currentWeekNumber * 7) + weeks[currentWeekNumber].indexOf(Clock.System.nowDate())

        val currentScheduleIndex = if (dayNumber / 7 % 2 == 0) startScheduleIndex
        else 1 - startScheduleIndex

        val dailySchedule: List<PairItem> = try {
            scheduleList[currentScheduleIndex][dayNumber % 7]
        } catch (_: Exception) {
            emptyList()
        }

        val dailyReplacement =
            replacement.find { it.date == Clock.System.nowDate() }

        return ScheduleUtil.scheduleWithReplacement(
            ScheduleUtil.groupDailyScheduleBySubgroup(dailySchedule),
            dailyReplacement,
            weekType == true,
            if (userIdentifier is UserIdentifier.Teacher) userIdentifier.name
            else null
        )
    }
}