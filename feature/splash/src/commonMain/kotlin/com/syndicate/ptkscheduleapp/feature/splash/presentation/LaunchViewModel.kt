package com.syndicate.ptkscheduleapp.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LaunchViewModel(
    settingsRepository: SettingsRepository
): ViewModel() {

    private val _selectedUserGroup = MutableStateFlow("")
    val selectedUserGroup = _selectedUserGroup.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userGroup
                .collect { userGroup ->
                    _selectedUserGroup.update { userGroup }
                }
        }
    }
}