package com.syndicate.ptkscheduleapp.feature.groups.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.groups.data.dto.AllGroupsDTO

internal interface RemoteGroupDataSource {
    suspend fun getGroupList(): ApiResponse<AllGroupsDTO>
}