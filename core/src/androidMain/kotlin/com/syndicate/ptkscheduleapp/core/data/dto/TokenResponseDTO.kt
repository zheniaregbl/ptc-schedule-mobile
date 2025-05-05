package com.syndicate.ptkscheduleapp.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponseDTO(
    @SerialName("body") val body: TokenResponseBody
)

@Serializable
internal data class TokenResponseBody(
    @SerialName("jwe") val jwe: String,
    @SerialName("ttl") val ttl: Int
)