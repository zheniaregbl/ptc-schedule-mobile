package com.syndicate.ptkscheduleapp.feature.schedule.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleInfoDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleResponseDTO
import kotlinx.serialization.json.JsonObject

internal interface RemoteScheduleDataSource {
    suspend fun getSchedule(group: String): ApiResponse<ScheduleResponseDTO>
    suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<JsonObject>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfoDTO>
}