package com.syndicate.ptkscheduleapp.feature.groups.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.groups.domain.model.AllGroups

internal interface GroupRepository {
    suspend fun getGroupList(): ApiResponse<AllGroups>
}