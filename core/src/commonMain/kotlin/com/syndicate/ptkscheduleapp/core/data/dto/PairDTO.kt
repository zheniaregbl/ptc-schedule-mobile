package com.syndicate.ptkscheduleapp.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PairDTO(
    @SerialName("dayOfWeek") val dayOfWeek: String,
    @SerialName("isUpper") val isUpper: Boolean? = null,
    @SerialName("pairNumber") val pairNumber: Int,
    @SerialName("subject") val subject: SubjectDTO,
    @SerialName("cabinet") val room: RoomDTO,
    @SerialName("teacher") val teacher: TeacherDTO,
    @SerialName("subgroupNumber") val subgroupNumber: Int,
    @SerialName("time") val time: String
)

@Serializable
data class RoomDTO(
    @SerialName("number") val number: String,
    @SerialName("place") val place: String
)

@Serializable
data class SubjectDTO(
    @SerialName("name") val name: String
)

@Serializable
data class TeacherDTO(
    @SerialName("fullName") val fullName: String
)