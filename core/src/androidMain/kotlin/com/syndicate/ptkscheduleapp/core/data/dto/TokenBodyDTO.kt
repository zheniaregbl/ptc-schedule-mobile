package com.syndicate.ptkscheduleapp.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenBodyDTO(
    @SerialName("keyId") val keyId: String,
    @SerialName("timestamp") val timestamp: String,
    @SerialName("signature") val signature: String
)
