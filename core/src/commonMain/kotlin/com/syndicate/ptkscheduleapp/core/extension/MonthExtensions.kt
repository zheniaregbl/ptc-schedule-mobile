package com.syndicate.ptkscheduleapp.core.extension

import kotlinx.datetime.Month

fun Month.plus(months: Int): Month {
    val values = Month.entries.toTypedArray()
    val newIndex = (this.ordinal + months % values.size + values.size) % values.size
    return values[newIndex]
}