package com.syndicate.ptkscheduleapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface PreferencesRepository {
    val userGroup: Flow<String>
    val localReplacement: Flow<String?>
    val lastUpdateScheduleTime: Flow<LocalDateTime?>
    val lastUpdateReplacementTime: Flow<LocalDateTime?>
    suspend fun saveGroup(group: String)
    suspend fun saveLocalSchedule(scheduleString: String)
    suspend fun saveLocalReplacement(replacementString: String)
    suspend fun saveLastUpdateScheduleTime(time: LocalDateTime)
    suspend fun saveLastUpdateReplacementTime(time: LocalDateTime)
    suspend fun getLocalSchedule(): String?
}