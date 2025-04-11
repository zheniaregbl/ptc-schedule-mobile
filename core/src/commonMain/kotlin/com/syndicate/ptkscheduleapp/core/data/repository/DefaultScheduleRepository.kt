package com.syndicate.ptkscheduleapp.core.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.data.network.RemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.serialization.json.JsonObject

class DefaultScheduleRepository(
    private val remoteScheduleDataSource: RemoteScheduleDataSource
): ScheduleRepository {

    override suspend fun getSchedule(userIdentifier: UserIdentifier): ApiResponse<List<PairItem>> {
        return remoteScheduleDataSource
            .getSchedule(userIdentifier)
            .suspendMapSuccess { listPair!!.map { it.toModel() } }
    }

    override suspend fun getReplacement(
        dateStart: String,
        dateEnd: String
    ): ApiResponse<JsonObject> {
        return remoteScheduleDataSource
            .getReplacement(dateStart, dateEnd)
            .suspendMapSuccess { replacements!! }
    }

    override suspend fun getScheduleInfo(): ApiResponse<ScheduleInfo> {
        return remoteScheduleDataSource
            .getScheduleInfo()
            .suspendMapSuccess { scheduleInfoDTO!!.toModel() }
    }
}