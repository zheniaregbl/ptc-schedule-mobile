package com.syndicate.ptkscheduleapp.feature.role.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class RoleViewModel(): ViewModel() {

    private val _selectedRoleIndex = MutableStateFlow(0)
    val selectedRoleIndex = _selectedRoleIndex.asStateFlow()

    fun onAction(action: RoleAction) {
        when (action) {
            is RoleAction.OnSelectRole -> selectUserRole(action.roleIndex)
            else -> Unit
        }
    }

    private fun selectUserRole(roleIndex: Int) =
        _selectedRoleIndex.update { roleIndex }
}