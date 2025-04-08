package com.syndicate.ptkscheduleapp.feature.teacher.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.syndicate.ptkscheduleapp.feature.teacher.data.network.RemoteTeacherDataSource
import com.syndicate.ptkscheduleapp.feature.teacher.domain.repository.TeacherRepository

internal class DefaultTeacherRepository(
    private val remoteTeacherDataSource: RemoteTeacherDataSource
): TeacherRepository {
    override suspend fun getTeacherList(): ApiResponse<List<String>> {
        return remoteTeacherDataSource.getTeacherList()
            .suspendMapSuccess { teachers ?: emptyList() }
    }
}