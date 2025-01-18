package com.syndicate.ptkscheduleapp.feature.groups.data.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.syndicate.ptkscheduleapp.feature.groups.data.dto.AllGroupsDTO
import io.ktor.client.HttpClient

internal class KtorRemoteGroupDataSource(
    private val httpClient: HttpClient
): RemoteGroupDataSource {
    override suspend fun getGroupList(): ApiResponse<AllGroupsDTO> {
        return httpClient.getApiResponse(
            urlString = "http://80.85.243.65:5001/api/settings/groups/get"
        )
    }
}