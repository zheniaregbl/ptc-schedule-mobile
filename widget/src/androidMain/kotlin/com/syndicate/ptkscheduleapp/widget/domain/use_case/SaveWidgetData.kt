package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.core.common.util.extension.shortName
import com.syndicate.ptkscheduleapp.core.data.mapper.toLocalDTO
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.format.DateTimeFormatter

class SaveWidgetData(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(schedule: List<List<PairItem>>) {

        val currentDayOfWeek = Clock.System.nowDate().dayOfWeek.shortName
        val dtf = DateTimeFormatter.ofPattern("HH:mm")
        val currentTime = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toJavaLocalDateTime()
            .format(dtf)

        preferencesRepository
            .saveWidgetSchedule(
                Json.encodeToString(schedule.map { weekSchedule -> weekSchedule.map { it.toLocalDTO() } })
            )

        preferencesRepository.saveLastUpdateWidgetTime("$currentDayOfWeek, $currentTime")
    }
}