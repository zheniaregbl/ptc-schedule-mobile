package com.syndicate.ptkscheduleapp.feature.schedule.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.feature.schedule.data.mapper.toModel
import com.syndicate.ptkscheduleapp.feature.schedule.data.network.RemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.feature.schedule.domain.repository.ScheduleRepository

internal class DefaultScheduleRepository(
    private val remoteScheduleDataSource: RemoteScheduleDataSource
): ScheduleRepository {

    override suspend fun getSchedule(group: String): ApiResponse<List<PairItem>> {
        return remoteScheduleDataSource
            .getSchedule(group)
            .suspendMapSuccess { listPair!!.map { it.toModel() } }
    }

    override suspend fun getReplacement(
        group: String,
        dateStart: String,
        dateEnd: String
    ): ApiResponse<List<ReplacementItem>> {
        return remoteScheduleDataSource
            .getReplacement(dateStart, dateEnd)
            .suspendMapSuccess { ScheduleUtil.getReplacementFromJson(replacements!!, group) }
    }

    override suspend fun getScheduleInfo(): ApiResponse<ScheduleInfo> {
        return remoteScheduleDataSource
            .getScheduleInfo()
            .suspendMapSuccess { scheduleInfoDTO!!.toModel() }
    }
}