package com.syndicate.ptkscheduleapp.core.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.data.dto.ReplacementResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleInfoResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleResponseDTO
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier

interface RemoteScheduleDataSource {
    suspend fun getSchedule(userIdentifier: UserIdentifier): ApiResponse<ScheduleResponseDTO>
    suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<ReplacementResponseDTO>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfoResponseDTO>
}