package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScheduleInfoResponseDTO(
    @SerialName("status") val statusResponse: String,
    @SerialName("message") val message: String,
    @SerialName("data") val scheduleInfoDTO: ScheduleInfoDTO? = null
)
