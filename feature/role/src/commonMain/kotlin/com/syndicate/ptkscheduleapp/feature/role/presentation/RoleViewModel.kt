package com.syndicate.ptkscheduleapp.feature.role.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RoleViewModel(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    private val _selectedRoleIndex = MutableStateFlow(0)
    val selectedRoleIndex = _selectedRoleIndex.asStateFlow()

    fun onAction(action: RoleAction) {
        when (action) {
            is RoleAction.OnSelectRole -> selectUserRole(action.roleIndex)
            else -> Unit
        }
    }

    private fun selectUserRole(roleIndex: Int) = viewModelScope.launch {
        preferencesRepository.saveRole(UserRole.entries[roleIndex])
        _selectedRoleIndex.update { roleIndex }
    }
}