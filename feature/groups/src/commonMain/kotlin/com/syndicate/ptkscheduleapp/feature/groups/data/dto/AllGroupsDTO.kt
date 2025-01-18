package com.syndicate.ptkscheduleapp.feature.groups.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AllGroupsDTO(
    @SerialName("1") val firstCourse: List<String>,
    @SerialName("2") val secondCourse: List<String>,
    @SerialName("3") val thirdCourse: List<String>,
    @SerialName("4") val fourthCourse: List<String>
)
