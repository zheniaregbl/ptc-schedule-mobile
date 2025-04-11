package com.syndicate.ptkscheduleapp.core.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.core.data.dto.ReplacementResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleInfoResponseDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleResponseDTO
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class KtorRemoteScheduleDataSource(
    private val httpClient: HttpClient
): RemoteScheduleDataSource {

    override suspend fun getSchedule(userIdentifier: UserIdentifier): ApiResponse<ScheduleResponseDTO> {
        return when (userIdentifier) {
            is UserIdentifier.Student ->
                httpClient.getApiResponse("schedule/get") {
                    parameter("group", userIdentifier.group)
                    parameter("scheduleType", "week")
                    parameter("withReplacement", false)
                }
            is UserIdentifier.Teacher ->
                httpClient.getApiResponse("schedule/teacher/get/all") {
                    parameter("teacherSurname", userIdentifier.name)
                }
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