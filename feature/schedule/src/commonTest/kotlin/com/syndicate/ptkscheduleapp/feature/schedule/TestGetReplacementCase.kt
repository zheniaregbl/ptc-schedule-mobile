package com.syndicate.ptkscheduleapp.feature.schedule

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.serialization.json.JsonObject

internal class TestGetReplacementCase(
    private val scheduleRepository: ScheduleRepository
) {

    suspend operator fun invoke(
        userIdentifier: UserIdentifier,
        dateStart: String = "",
        dateEnd: String = "",
    ): CaseResult<List<ReplacementItem>> {

        return when (val response = scheduleRepository.getReplacement(dateStart, dateEnd)) {

            is ApiResponse.Failure.Error -> CaseResult.Error("Error getReplacement")

            is ApiResponse.Failure.Exception -> CaseResult.Error("Exception getReplacement")

            is ApiResponse.Success<JsonObject> -> {

                val replacement = when (userIdentifier) {
                    is UserIdentifier.Student ->
                        ScheduleUtil.getReplacementFromJsonForStudent(
                            response.data,
                            userIdentifier.group
                        )
                    is UserIdentifier.Teacher ->
                        ScheduleUtil.getReplacementFromJsonForTeacher(
                            response.data,
                            userIdentifier.name
                        )
                }

                CaseResult.Success(replacement)
            }
        }
    }
}