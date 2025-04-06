package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonObject

internal class GetReplacementCase(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(
        userGroup: String,
        lastUpdateTime: LocalDateTime?,
        dateStart: String = "",
        dateEnd: String = "",
    ): CaseResult<List<ReplacementItem>> {

        return when (val response = scheduleRepository.getReplacement(dateStart, dateEnd)) {

            is ApiResponse.Failure.Error -> CaseResult.Error("Error getReplacement")

            is ApiResponse.Failure.Exception -> CaseResult.Error("Exception getReplacement")

            is ApiResponse.Success<JsonObject> -> {

                lastUpdateTime?.let {
                    if (preferencesRepository.getLastUpdateReplacementTime() != lastUpdateTime) {
                        preferencesRepository.saveLocalReplacement(response.data.toString())
                        preferencesRepository.saveLastUpdateReplacementTime(lastUpdateTime)
                    }
                }

                CaseResult.Success(ScheduleUtil.getReplacementFromJson(response.data, userGroup))
            }
        }
    }
}