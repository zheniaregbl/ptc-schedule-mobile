package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository

internal class GetWeekTypeCase(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): Boolean {
        return when (val response = scheduleRepository.getScheduleInfo()) {
            is ApiResponse.Failure.Error -> preferencesRepository.getLocalWeekType()
            is ApiResponse.Failure.Exception -> preferencesRepository.getLocalWeekType()
            is ApiResponse.Success<ScheduleInfo> -> {
                preferencesRepository.saveLocalWeekType(response.data.isUpperWeek!!)
                response.data.isUpperWeek!!
            }
        }
    }
}