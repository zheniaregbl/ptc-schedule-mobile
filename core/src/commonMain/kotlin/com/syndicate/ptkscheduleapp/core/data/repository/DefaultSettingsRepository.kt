package com.syndicate.ptkscheduleapp.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.syndicate.ptkscheduleapp.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultSettingsRepository(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    private object PreferencesKeys {
        val userGroupKey = stringPreferencesKey("user_group")
    }

    override val userGroup: Flow<String> = dataStore
        .data
        .map {
            if (it[PreferencesKeys.userGroupKey].isNullOrBlank()) ""
            else it[PreferencesKeys.userGroupKey]!!
        }

    override suspend fun setGroup(group: String) {
        dataStore.edit { it[PreferencesKeys.userGroupKey] = group }
    }
}