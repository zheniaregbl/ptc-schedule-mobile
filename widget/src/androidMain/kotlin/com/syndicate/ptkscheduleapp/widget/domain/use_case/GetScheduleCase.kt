package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import kotlinx.serialization.json.Json

class GetScheduleCase(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(): List<List<PairItem>>? {

        val userGroup = preferencesRepository.getUserGroup()

        return when (val response = scheduleRepository.getSchedule(userGroup)) {

            is ApiResponse.Success<List<PairItem>> -> {
                ScheduleUtil.getWeekSchedule(response.data)
            }

            else -> {

                preferencesRepository.getLocalSchedule()?.let { scheduleString ->
                    val localSchedule = Json
                        .decodeFromString<List<PairDTO>>(scheduleString)
                        .map { it.toModel() }
                    return ScheduleUtil.getWeekSchedule(localSchedule)
                }

                return null
            }
        }
    }
}