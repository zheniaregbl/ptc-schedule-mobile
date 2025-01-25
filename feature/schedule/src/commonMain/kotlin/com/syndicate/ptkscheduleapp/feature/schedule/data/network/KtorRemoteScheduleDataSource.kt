package com.syndicate.ptkscheduleapp.feature.schedule.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleInfoDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import kotlinx.serialization.json.JsonObject

internal class KtorRemoteScheduleDataSource(
    private val httpClient: HttpClient
): RemoteScheduleDataSource {

    override suspend fun getSchedule(group: String): ApiResponse<ScheduleResponseDTO> {
        return httpClient.getApiResponse("schedule/get") {
            parameter("group", group)
            parameter("schedule_type", "week")
            parameter("withReplacement", false)
        }
    }

    override suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<JsonObject> {
        return httpClient.getApiResponse("replacements/get") {
            parameter("dateStart", dateStart)
            parameter("dateEnd", dateEnd)
        }
    }

    override suspend fun getScheduleInfo(): ApiResponse<ScheduleInfoDTO> {
        return httpClient.getApiResponse("settings/config/get")
    }
}