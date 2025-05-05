package com.syndicate.ptkscheduleapp.feature.groups.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.BuildConfigProvider
import com.syndicate.ptkscheduleapp.feature.groups.data.dto.GroupResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

internal class KtorRemoteGroupDataSource(
    private val httpClient: HttpClient
): RemoteGroupDataSource {

    override suspend fun getGroupList(course: Int): ApiResponse<GroupResponseDTO> {
        return httpClient.getApiResponse("${BuildConfigProvider.BASE_URL}settings/groups/get") {
            parameter("course", course)
        }
    }
}