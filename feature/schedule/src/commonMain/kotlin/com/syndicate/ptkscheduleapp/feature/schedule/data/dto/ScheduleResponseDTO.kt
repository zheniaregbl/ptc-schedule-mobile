package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScheduleResponseDTO(
    @SerialName("result") val listPair: List<PairDTO>
)
