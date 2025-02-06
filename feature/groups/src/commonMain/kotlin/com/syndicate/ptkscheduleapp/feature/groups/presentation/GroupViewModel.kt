package com.syndicate.ptkscheduleapp.feature.groups.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.feature.groups.domain.repository.GroupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class GroupViewModel(
    private val groupRepository: GroupRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GroupState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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

        delay(2000)

        groupRepository.getGroupList(_state.value.selectedCourseIndex + 1)
            .onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    groupList = data
                ) }
            }
            .onError {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = "Ошибка ${statusCode.code} при получении групп",
                    groupList = emptyList()
                ) }
            }
            .onException {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = "Ошибка при попытке получения групп",
                    groupList = emptyList()
                ) }
            }
    }

    private fun selectGroup(group: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
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