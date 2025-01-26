package com.syndicate.ptkscheduleapp.feature.groups.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.groups.data.dto.GroupResponseDTO

internal interface RemoteGroupDataSource {
    suspend fun getGroupList(course: Int): ApiResponse<GroupResponseDTO>
}