package com.syndicate.ptkscheduleapp.feature.groups.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GroupResponseDTO(
    @SerialName("status") val statusResponse: String,
    @SerialName("message") val message: String,
    @SerialName("data") val groups: List<String>? = null
)
