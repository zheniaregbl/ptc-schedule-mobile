package com.syndicate.ptkscheduleapp.feature.groups.domain.model

internal data class AllGroups(
    val firstCourse: List<String> = listOf(),
    val secondCourse: List<String> = listOf(),
    val thirdCourse: List<String> = listOf(),
    val fourthCourse: List<String> = listOf()
)
