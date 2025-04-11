package com.syndicate.ptkscheduleapp.feature.groups.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.groups.domain.use_case.GetGroupListCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class GroupViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val getGroupListCase: GetGroupListCase
) : ViewModel() {

    private val _state = MutableStateFlow(GroupState())
    val state = _state.asStateFlow()

    fun onAction(action: GroupAction) {

        when (action) {

            GroupAction.GetGroupList -> getGroupList()

            is GroupAction.OnChangeCourse ->
                _state.update { it.copy(selectedCourseIndex = action.course) }

            is GroupAction.OnSelectGroup ->
                selectGroup(action.group)

            GroupAction.HideErrorMessage ->
                _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun getGroupList() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true) }

        delay(500)

        when (val result = getGroupListCase(_state.value.selectedCourseIndex + 1)) {

            is CaseResult.Error ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = result.message,
                    groupList = emptyList()
                ) }

            is CaseResult.Success<List<String>> ->
                _state.update { it.copy(
                    isLoading = false,
                    groupList = result.data
                ) }
        }
    }

    private fun selectGroup(group: String) = viewModelScope.launch {

        _state.update { it.copy(isLoading = true) }

        preferencesRepository.saveRole(UserRole.STUDENT)
        preferencesRepository.saveGroup(group)

        preferencesRepository.userGroup
            .collect { userGroup ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        selectedGroup = userGroup
                    )
                }
            }
    }
}