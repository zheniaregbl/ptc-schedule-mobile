package com.syndicate.ptkscheduleapp.core.common.util.extension

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.DayOfWeek.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Clock.System.nowDate() =
    this.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

val DayOfWeek.shortName: String
    get() = when (this) {
        MONDAY -> "пн"
        TUESDAY -> "вт"
        WEDNESDAY -> "ср"
        THURSDAY -> "чт"
        FRIDAY -> "пт"
        SATURDAY -> "сб"
        SUNDAY -> "вс"
        else -> "пн"
    }