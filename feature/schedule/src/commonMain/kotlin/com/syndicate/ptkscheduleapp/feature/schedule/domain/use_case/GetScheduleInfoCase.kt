package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.message
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.Logger

internal class GetScheduleInfoCase(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(): CaseResult<ScheduleInfo> {
        return when (val response = scheduleRepository.getScheduleInfo()) {
            is ApiResponse.Failure.Error -> {
                Logger.error(response.message())
                CaseResult.Error("Ошибка ${response.statusCode} при получения типа недели")
            }
            is ApiResponse.Failure.Exception -> {
                Logger.error(response.message())
                CaseResult.Error("Ошибка при получения типа недели")
            }
            is ApiResponse.Success<ScheduleInfo> -> CaseResult.Success(response.data)
        }
    }
}