package com.syndicate.ptkscheduleapp.feature.teacher.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.BuildConfigProvider
import com.syndicate.ptkscheduleapp.feature.teacher.data.dto.TeacherResponseDTO
import io.ktor.client.HttpClient

internal class KtorRemoteTeacherDataSource(
    private val httpClient: HttpClient
): RemoteTeacherDataSource {

    override suspend fun getTeacherList(): ApiResponse<TeacherResponseDTO> {
        return httpClient.getApiResponse("${BuildConfigProvider.BASE_URL}schedule/teachers")
    }
}