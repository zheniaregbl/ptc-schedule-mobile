package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class GetReplacementCase(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(
        dateStart: String = "",
        dateEnd: String = ""
    ): List<ReplacementItem>? {

        val userGroup = preferencesRepository.getUserGroup()

        return when (val response = scheduleRepository.getReplacement(dateStart, dateEnd)) {

            is ApiResponse.Success<JsonObject> -> {
                preferencesRepository.saveLocalReplacement(response.data.toString())
                ScheduleUtil.getReplacementFromJson(response.data, userGroup)
            }

            else -> {
                preferencesRepository.getLocalReplacement()?.let { replacementString ->
                    return ScheduleUtil.getReplacementFromJson(
                        Json.decodeFromString<JsonObject>(replacementString),
                        userGroup
                    )
                }
                return null
            }
        }
    }
}