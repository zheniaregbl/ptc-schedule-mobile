package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository

internal class GetLocalWeekTypeCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): Boolean {
        return preferencesRepository.getLocalWeekType()
    }
}