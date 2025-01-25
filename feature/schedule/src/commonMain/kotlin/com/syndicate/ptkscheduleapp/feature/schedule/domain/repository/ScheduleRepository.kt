package com.syndicate.ptkscheduleapp.feature.schedule.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ScheduleInfo
import kotlinx.serialization.json.JsonObject

internal interface ScheduleRepository {
    suspend fun getSchedule(group: String): ApiResponse<List<PairItem>>
    suspend fun getReplacement(
        group: String,
        dateStart: String = "",
        dateEnd: String = "",
    ): ApiResponse<List<ReplacementItem>>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfo>
}