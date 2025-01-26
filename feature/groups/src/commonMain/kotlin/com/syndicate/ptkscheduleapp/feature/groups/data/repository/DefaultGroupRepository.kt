package com.syndicate.ptkscheduleapp.feature.groups.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.syndicate.ptkscheduleapp.feature.groups.data.network.RemoteGroupDataSource
import com.syndicate.ptkscheduleapp.feature.groups.domain.repository.GroupRepository

internal class DefaultGroupRepository(
    private val remoteGroupDataSource: RemoteGroupDataSource
): GroupRepository {
    override suspend fun getGroupList(course: Int): ApiResponse<List<String>> {
        return remoteGroupDataSource.getGroupList(course)
            .suspendMapSuccess { groups ?: emptyList() }
    }
}