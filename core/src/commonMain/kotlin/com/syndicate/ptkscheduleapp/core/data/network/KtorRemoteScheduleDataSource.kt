package com.syndicate.ptkscheduleapp.core.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.core.data.dto.ReplacementResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleInfoResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class KtorRemoteScheduleDataSource(
    private val httpClient: HttpClient
): RemoteScheduleDataSource {

    override suspend fun getSchedule(group: String): ApiResponse<ScheduleResponseDTO> {
        return httpClient.getApiResponse("schedule/get") {
            parameter("group", group)
            parameter("scheduleType", "week")
            parameter("withReplacement", false)
        }
    }

    override suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<ReplacementResponseDTO> {
        return httpClient.getApiResponse("replacements/get") {
            parameter("dateStart", dateStart)
            parameter("dateEnd", dateEnd)
        }
    }

    override suspend fun getScheduleInfo(): ApiResponse<ScheduleInfoResponseDTO> {
        return httpClient.getApiResponse("settings/config/get")
    }
}