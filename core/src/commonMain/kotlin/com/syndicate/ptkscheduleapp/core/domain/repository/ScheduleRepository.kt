package com.syndicate.ptkscheduleapp.core.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.serialization.json.JsonObject

interface ScheduleRepository {
    suspend fun getSchedule(userIdentifier: UserIdentifier): ApiResponse<List<PairItem>>
    suspend fun getReplacement(
        dateStart: String = "",
        dateEnd: String = "",
        userIdentifier: UserIdentifier
    ): ApiResponse<JsonObject>
    suspend fun getScheduleInfo(): ApiResponse<ScheduleInfo>
}