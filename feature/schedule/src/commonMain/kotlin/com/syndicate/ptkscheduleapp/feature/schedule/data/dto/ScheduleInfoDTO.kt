package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScheduleInfoDTO(
    @SerialName("week_is_upper") val isUpperWeek: Boolean,
    @SerialName("last_schedule_update_time") private val _lastScheduleUpdateTime: String,
    @SerialName("last_replacement_update_time") private val _lastReplacementUpdateTime: String,
    @SerialName("groups_info") val groupInfo: List<GroupScheduleUpdateInfoDTO>
) {

    val lastScheduleUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastScheduleUpdateTime)

    val lastReplacementUpdateTime: LocalDateTime
        get() = LocalDateTime.parse(_lastReplacementUpdateTime)
}