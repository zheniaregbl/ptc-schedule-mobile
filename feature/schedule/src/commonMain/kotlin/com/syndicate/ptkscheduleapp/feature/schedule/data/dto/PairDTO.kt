package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PairDTO(
    @SerialName("dayOfWeek") val dayOfWeek: String,
    @SerialName("isUpper") val isUpper: Boolean? = null,
    @SerialName("pairNumber") val pairNumber: Int,
    @SerialName("subject") val subject: String,
    @SerialName("cabinet") val room: String,
    @SerialName("place") val place: String,
    @SerialName("teacher") val teacher: String,
    @SerialName("subgroupNumber") val subgroupNumber: Int,
    @SerialName("time") val time: String
)
