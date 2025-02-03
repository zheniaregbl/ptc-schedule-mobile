package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GroupScheduleUpdateInfoDTO(
    @SerialName("groupNumber") val group: String,
    @SerialName("lastUpdateTime") private val _lastUpdateTime: String
) {
    val lastUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastUpdateTime)
}