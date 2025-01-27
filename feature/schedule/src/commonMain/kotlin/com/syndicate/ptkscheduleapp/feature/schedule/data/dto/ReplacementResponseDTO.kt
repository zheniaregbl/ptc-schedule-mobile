package com.syndicate.ptkscheduleapp.feature.schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class ReplacementResponseDTO(
    @SerialName("status") val statusResponse: String,
    @SerialName("message") val message: String,
    @SerialName("data") val replacements: JsonObject? = null
)
