package com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun Clock.System.nowDate() =
    this.now().toLocalDateTime(TimeZone.currentSystemDefault()).date