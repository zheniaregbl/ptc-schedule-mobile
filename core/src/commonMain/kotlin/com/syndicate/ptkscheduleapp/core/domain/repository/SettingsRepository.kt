package com.syndicate.ptkscheduleapp.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userGroup: Flow<String>
    suspend fun setGroup(group: String)
}