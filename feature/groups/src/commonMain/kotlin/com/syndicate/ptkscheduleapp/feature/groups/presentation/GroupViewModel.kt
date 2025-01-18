package com.syndicate.ptkscheduleapp.feature.groups.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syndicate.ptkscheduleapp.feature.groups.domain.repository.GroupRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class GroupViewModel(
    private val groupRepository: GroupRepository
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
        }
    }

    private fun getGroupList() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true) }

        groupRepository.getGroupList()
            .suspendOnSuccess {
                val groupList = when (_state.value.selectedCourseIndex) {
                    0 -> data.firstCourse
                    1 -> data.secondCourse
                    2 -> data.thirdCourse
                    else -> data.fourthCourse
                }
                _state.update { it.copy(
                    isLoading = false,
                    groupList = groupList
                ) }
            }
            .suspendOnError { println("ERROR") }
            .suspendOnFailure { println("FAILURE") }
            .suspendOnException { println("EXCEPTION") }
    }
}