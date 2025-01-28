package com.syndicate.ptkscheduleapp.feature.schedule.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal data class ScheduleInfo(
    val isUpperWeek: Boolean? = null,
    val lastScheduleUpdateTime: LocalDateTime = Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastReplacementUpdateTime: LocalDateTime = Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val groupInfo: List<GroupScheduleUpdateInfo> = emptyList()
)
