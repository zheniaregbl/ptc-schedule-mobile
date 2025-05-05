package com.syndicate.ptkscheduleapp.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AppVersionsResponseDTO(
    @SerialName("body") val body: AppVersionsResponseBody
)

@Serializable
internal data class AppVersionsResponseBody(
    @SerialName("content") val content: List<AppVersionData>
)

@Serializable
internal data class AppVersionData(
    @SerialName("versionName") val versionName: String,
    @SerialName("versionCode") val versionCode: Int,
    @SerialName("versionStatus") val status: String,
    @SerialName("whatsNew") val info: String
)