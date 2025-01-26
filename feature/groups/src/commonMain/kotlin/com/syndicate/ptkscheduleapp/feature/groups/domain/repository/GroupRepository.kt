package com.syndicate.ptkscheduleapp.feature.groups.domain.repository

import com.skydoves.sandwich.ApiResponse

internal interface GroupRepository {
    suspend fun getGroupList(course: Int): ApiResponse<List<String>>
}