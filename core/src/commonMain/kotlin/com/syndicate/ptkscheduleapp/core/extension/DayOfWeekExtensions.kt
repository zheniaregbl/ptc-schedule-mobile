package com.syndicate.ptkscheduleapp.core.extension

import kotlinx.datetime.DayOfWeek

fun DayOfWeek.plus(days: Int): DayOfWeek {
    val values = DayOfWeek.entries.toTypedArray()
    val newIndex = (this.ordinal + days % values.size + values.size) % values.size
    return values[newIndex]
}