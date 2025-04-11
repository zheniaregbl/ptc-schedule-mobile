package com.syndicate.ptkscheduleapp.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LaunchViewModel(
    preferencesRepository: PreferencesRepository
): ViewModel() {

    private val _selectedUserRole: MutableStateFlow<UserRole?> = MutableStateFlow(null)
    val selectedUserRole = _selectedUserRole.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedUserRole.update { preferencesRepository.getUserRole() }
        }
    }
}