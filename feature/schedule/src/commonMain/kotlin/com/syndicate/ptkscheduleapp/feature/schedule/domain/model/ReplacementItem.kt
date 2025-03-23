package com.syndicate.ptkscheduleapp.feature.schedule.domain.model

import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal data class ReplacementItem(
    val date: LocalDate = Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date,
    val listReplacement: List<List<PairItem>> = listOf(listOf(PairItem()))
)
