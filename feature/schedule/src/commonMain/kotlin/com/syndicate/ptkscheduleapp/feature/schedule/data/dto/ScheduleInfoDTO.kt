package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScheduleInfoDTO(
    @SerialName("weekIsUpper") val isUpperWeek: Boolean,
    @SerialName("lastScheduleUpdateTime") private val _lastScheduleUpdateTime: String,
    @SerialName("lastReplacementUpdateTime") private val _lastReplacementUpdateTime: String,
    @SerialName("groupsInfo") val groupInfo: List<GroupScheduleUpdateInfoDTO>
) {

    val lastScheduleUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastScheduleUpdateTime)

    val lastReplacementUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastReplacementUpdateTime)
}