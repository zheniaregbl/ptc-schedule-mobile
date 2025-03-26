package com.syndicate.ptkscheduleapp.core.common.util.extension

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Clock.System.nowDate() =
    this.now().toLocalDateTime(TimeZone.currentSystemDefault()).date