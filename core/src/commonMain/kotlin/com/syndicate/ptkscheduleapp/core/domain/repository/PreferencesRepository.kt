package com.syndicate.ptkscheduleapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface PreferencesRepository {
    val userGroup: Flow<String>
    suspend fun saveGroup(group: String)
    suspend fun saveLocalSchedule(scheduleString: String)
    suspend fun saveLocalReplacement(replacementString: String)
    suspend fun saveLastUpdateScheduleTime(time: LocalDateTime)
    suspend fun saveLastUpdateReplacementTime(time: LocalDateTime)
    suspend fun getUserGroup(): String
    suspend fun getLocalSchedule(): String?
    suspend fun getLocalReplacement(): String?
    suspend fun getLastUpdateScheduleTime(): LocalDateTime?
    suspend fun getLastUpdateReplacementTime(): LocalDateTime?
}