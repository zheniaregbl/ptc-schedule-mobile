package com.syndicate.ptkscheduleapp.feature.schedule.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ScheduleInfo
import kotlinx.serialization.json.JsonObject

internal interface ScheduleRepository {
    suspend fun getSchedule(group: String): ApiResponse<List<PairItem>>
    suspend fun getReplacement(
        dateStart: String = "",
        dateEnd: String = "",
    ): ApiResponse<JsonObject>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfo>
}