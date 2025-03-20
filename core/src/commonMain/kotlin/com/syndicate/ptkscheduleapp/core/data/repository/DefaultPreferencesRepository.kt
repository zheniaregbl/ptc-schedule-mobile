package com.syndicate.ptkscheduleapp.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class DefaultPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferencesKeys {
        val userThemeModeKey = stringPreferencesKey("user_theme_mode")
        val userGroupKey = stringPreferencesKey("user_group")
        val localScheduleKey = stringPreferencesKey("local_schedule")
        val localReplacementKey = stringPreferencesKey("local_replacement")
        val lastUpdateScheduleTimeKey = stringPreferencesKey("last_update_schedule_time")
        val lastUpdateReplacementTimeKey = stringPreferencesKey("last_update_replacement_time")
    }

    override val userGroup: Flow<String> = dataStore
        .data
        .map { it[PreferencesKeys.userGroupKey] ?: "" }

    override val userThemeMode: Flow<ThemeMode> = dataStore
        .data
        .map { ThemeMode.valueOf(it[PreferencesKeys.userThemeModeKey] ?: "LIGHT") }

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { it[PreferencesKeys.userThemeModeKey] = themeMode.toString() }
    }

    override suspend fun saveGroup(group: String) {
        dataStore.edit { it[PreferencesKeys.userGroupKey] = group }
    }

    override suspend fun saveLocalSchedule(scheduleString: String) {
        dataStore.edit { it[PreferencesKeys.localScheduleKey] = scheduleString }
    }

    override suspend fun saveLocalReplacement(replacementString: String) {
        dataStore.edit { it[PreferencesKeys.localReplacementKey] = replacementString }
    }

    override suspend fun saveLastUpdateScheduleTime(time: LocalDateTime) {
        dataStore.edit { it[PreferencesKeys.lastUpdateScheduleTimeKey] = time.toString() }
    }

    override suspend fun saveLastUpdateReplacementTime(time: LocalDateTime) {
        dataStore.edit { it[PreferencesKeys.lastUpdateReplacementTimeKey] = time.toString() }
    }

    override suspend fun getUserGroup(): String =
        dataStore.data.map { it[PreferencesKeys.userGroupKey] }.first() ?: ""

    override suspend fun getLocalSchedule(): String? =
        dataStore.data.map { it[PreferencesKeys.localScheduleKey] }.first()

    override suspend fun getLocalReplacement(): String? =
        dataStore.data.map { it[PreferencesKeys.localReplacementKey] }.first()

    override suspend fun getLastUpdateScheduleTime(): LocalDateTime? = dataStore.data
        .map {
            if (it[PreferencesKeys.lastUpdateScheduleTimeKey].isNullOrBlank()) null
            else LocalDateTime.parse(it[PreferencesKeys.lastUpdateScheduleTimeKey]!!)
        }
        .first()

    override suspend fun getLastUpdateReplacementTime(): LocalDateTime? = dataStore.data
        .map {
            if (it[PreferencesKeys.lastUpdateReplacementTimeKey].isNullOrBlank()) null
            else LocalDateTime.parse(it[PreferencesKeys.lastUpdateReplacementTimeKey]!!)
        }
        .first()
}