package com.syndicate.ptkscheduleapp.core.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class GroupScheduleUpdateInfo(
    val group: String = "1991",
    val lastUpdateTime: LocalDateTime = Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
)
