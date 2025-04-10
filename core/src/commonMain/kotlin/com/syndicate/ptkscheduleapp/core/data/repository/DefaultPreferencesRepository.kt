package com.syndicate.ptkscheduleapp.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
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
        val userRoleKey = stringPreferencesKey("user_role")
        val userGroupKey = stringPreferencesKey("user_group")
        val userTeacherKey = stringPreferencesKey("user_teacher")
        val localWeekType = booleanPreferencesKey("week_type")
        val localScheduleKey = stringPreferencesKey("local_schedule")
        val localReplacementKey = stringPreferencesKey("local_replacement")
        val lastUpdateScheduleTimeKey = stringPreferencesKey("last_update_schedule_time")
        val lastUpdateReplacementTimeKey = stringPreferencesKey("last_update_replacement_time")
        val lastUpdateWidgetTimeKey = stringPreferencesKey("last_update_widget_time")
        val widgetScheduleKey = stringPreferencesKey("widget_schedule")
    }

    override val userGroup: Flow<String> = dataStore
        .data
        .map { it[PreferencesKeys.userGroupKey] ?: "" }

    override val userTeacher: Flow<String> = dataStore
        .data
        .map { it[PreferencesKeys.userTeacherKey] ?: "" }

    override val userThemeMode: Flow<ThemeMode> = dataStore
        .data
        .map { ThemeMode.valueOf(it[PreferencesKeys.userThemeModeKey] ?: "LIGHT") }

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { it[PreferencesKeys.userThemeModeKey] = themeMode.toString() }
    }

    override suspend fun saveGroup(group: String) {
        dataStore.edit { it[PreferencesKeys.userGroupKey] = group }
    }

    override suspend fun saveTeacher(teacher: String) {
        dataStore.edit { it[PreferencesKeys.userTeacherKey] = teacher }
    }

    override suspend fun saveRole(role: UserRole) {
        dataStore.edit { it[PreferencesKeys.userRoleKey] = role.toString() }
    }

    override suspend fun saveLocalWeekType(isUpperWeek: Boolean) {
        dataStore.edit { it[PreferencesKeys.localWeekType] = isUpperWeek }
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

    override suspend fun saveLastUpdateWidgetTime(time: String) {
        dataStore.edit { it[PreferencesKeys.lastUpdateWidgetTimeKey] = time }
    }

    override suspend fun saveWidgetSchedule(schedule: String) {
        dataStore.edit { it[PreferencesKeys.widgetScheduleKey] = schedule }
    }

    override suspend fun getUserGroup(): String =
        dataStore.data.map { it[PreferencesKeys.userGroupKey] }.first() ?: ""

    override suspend fun getUserTeacher(): String =
        dataStore.data.map { it[PreferencesKeys.userTeacherKey] }.first() ?: ""

    override suspend fun getUserRole(): UserRole? = dataStore.data
        .map {
            if (it[PreferencesKeys.userRoleKey].isNullOrBlank()) null
            else UserRole.valueOf(it[PreferencesKeys.userRoleKey]!!)
        }
        .first()

    override suspend fun getLocalWeekType(): Boolean =
        dataStore.data.map { it[PreferencesKeys.localWeekType] ?: false }.first()

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

    override suspend fun getLastUpdateWidgetTime(): String? = dataStore.data
        .map {
            if (it[PreferencesKeys.lastUpdateWidgetTimeKey].isNullOrBlank()) null
            else it[PreferencesKeys.lastUpdateWidgetTimeKey]!!
        }
        .first()

    override suspend fun getWidgetSchedule(): String? = dataStore.data
        .map {
            if (it[PreferencesKeys.widgetScheduleKey].isNullOrBlank()) null
            else it[PreferencesKeys.widgetScheduleKey]!!
        }
        .first()
}