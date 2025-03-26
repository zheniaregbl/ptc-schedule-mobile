package com.syndicate.ptkscheduleapp.core.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class ReplacementItem(
    val date: LocalDate = Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date,
    val listReplacement: List<List<PairItem>> = listOf(listOf(PairItem()))
)
