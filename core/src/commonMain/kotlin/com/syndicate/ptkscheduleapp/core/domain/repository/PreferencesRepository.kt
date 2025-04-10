package com.syndicate.ptkscheduleapp.core.domain.repository

import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface PreferencesRepository {
    val userGroup: Flow<String>
    val userThemeMode: Flow<ThemeMode>
    suspend fun saveThemeMode(themeMode: ThemeMode)
    suspend fun saveGroup(group: String)
    suspend fun saveRole(role: UserRole)
    suspend fun saveLocalWeekType(isUpperWeek: Boolean)
    suspend fun saveLocalSchedule(scheduleString: String)
    suspend fun saveLocalReplacement(replacementString: String)
    suspend fun saveLastUpdateScheduleTime(time: LocalDateTime)
    suspend fun saveLastUpdateReplacementTime(time: LocalDateTime)
    suspend fun saveLastUpdateWidgetTime(time: String)
    suspend fun saveWidgetSchedule(schedule: String)
    suspend fun getUserGroup(): String
    suspend fun getUserRole(): UserRole?
    suspend fun getLocalWeekType(): Boolean
    suspend fun getLocalSchedule(): String?
    suspend fun getLocalReplacement(): String?
    suspend fun getLastUpdateScheduleTime(): LocalDateTime?
    suspend fun getLastUpdateReplacementTime(): LocalDateTime?
    suspend fun getLastUpdateWidgetTime(): String?
    suspend fun getWidgetSchedule(): String?
}