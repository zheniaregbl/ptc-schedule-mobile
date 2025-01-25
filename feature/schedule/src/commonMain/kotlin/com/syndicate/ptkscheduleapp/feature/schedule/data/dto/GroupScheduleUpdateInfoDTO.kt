package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GroupScheduleUpdateInfoDTO(
    @SerialName("group_number") val group: String,
    @SerialName("last_update_time") private val _lastUpdateTime: String
) {
    val lastUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastUpdateTime)
}