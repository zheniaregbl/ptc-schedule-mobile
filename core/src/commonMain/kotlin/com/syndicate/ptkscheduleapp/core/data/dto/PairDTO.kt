package com.syndicate.ptkscheduleapp.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PairDTO(
    @SerialName("dayOfWeek") val dayOfWeek: String,
    @SerialName("isUpper") val isUpper: Boolean? = null,
    @SerialName("pairNumber") val pairNumber: Int,
    @SerialName("subject") val subject: SubjectDTO,
    @SerialName("group") val group: GroupDTO,
    @SerialName("cabinet") val room: RoomDTO,
    @SerialName("teacher") val teacher: TeacherDTO,
    @SerialName("subgroupNumber") val subgroupNumber: Int,
    @SerialName("time") val time: String,
    @SerialName("replacement_info") val replacementInfoDTO: ReplacementInfoDTO? = null
)

@Serializable
data class RoomDTO(
    @SerialName("number") val number: String,
    @SerialName("place") val place: String
)

@Serializable
data class GroupDTO(
    @SerialName("name") val name: String
)

@Serializable
data class SubjectDTO(
    @SerialName("name") val name: String
)

@Serializable
data class TeacherDTO(
    @SerialName("fullName") val fullName: String
)

@Serializable
data class ReplacementInfoDTO(
    @SerialName("is_replacement") val isReplacement: Boolean,
    @SerialName("previous_pair_item") val previousPairNumber: Int,
    @SerialName("is_new_pair") val isNewPair: Boolean,
    @SerialName("swap_pair") val swapPair: Boolean,
)