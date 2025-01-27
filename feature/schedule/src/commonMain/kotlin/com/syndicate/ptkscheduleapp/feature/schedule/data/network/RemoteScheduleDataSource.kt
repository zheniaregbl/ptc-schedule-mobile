package com.syndicate.ptkscheduleapp.feature.schedule.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ReplacementResponseDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleInfoDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleInfoResponseDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleResponseDTO

internal interface RemoteScheduleDataSource {
    suspend fun getSchedule(group: String): ApiResponse<ScheduleResponseDTO>
    suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<ReplacementResponseDTO>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfoResponseDTO>
}