package com.syndicate.ptkscheduleapp.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LaunchViewModel(
    preferencesRepository: PreferencesRepository
): ViewModel() {

    private val _selectedUserTeacher = MutableStateFlow("")
    val selectedUserTeacher = _selectedUserTeacher.asStateFlow()

    private val _selectedUserGroup = MutableStateFlow("")
    val selectedUserGroup = _selectedUserGroup.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedUserGroup.update { preferencesRepository.getUserGroup() }
            _selectedUserTeacher.update { preferencesRepository.getUserTeacher() }
        }
    }
}