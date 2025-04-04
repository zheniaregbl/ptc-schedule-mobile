package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository

internal class GetScheduleInfoCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(): CaseResult<ScheduleInfo> {
        return when (val response = scheduleRepository.getScheduleInfo()) {
            is ApiResponse.Failure.Error -> CaseResult.Error("Error getScheduleInfo")
            is ApiResponse.Failure.Exception -> CaseResult.Error("Exception getScheduleInfo")
            is ApiResponse.Success<ScheduleInfo> -> CaseResult.Success(response.data)
        }
    }
}