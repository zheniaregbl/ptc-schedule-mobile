package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.serialization.json.Json

internal class GetLocalScheduleCase(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(): List<List<PairItem>>? {

        preferencesRepository.getLocalSchedule()?.let { scheduleString ->

            val localSchedule = Json
                .decodeFromString<List<PairDTO>>(scheduleString)
                .map { it.toModel() }

            return ScheduleUtil.getWeekSchedule(localSchedule)
        }

        return null
    }
}