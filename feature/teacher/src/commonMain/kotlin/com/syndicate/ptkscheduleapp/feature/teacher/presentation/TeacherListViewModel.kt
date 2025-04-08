package com.syndicate.ptkscheduleapp.feature.teacher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.GetTeacherListByNameCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class TeacherListViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val getTeacherListByNameCase: GetTeacherListByNameCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(TeacherListState())
    val state = _state
        .onStart { observeSearchTeacher() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(30_000L),
            _state.value
        )

    fun onAction(action: TeacherListAction) {

        when (action) {

            is TeacherListAction.OnSearchTeacherChange ->
                _state.update { it.copy(
                    searchTeacherText = action.teacherName
                ) }

            is TeacherListAction.OnSelectTeacherList -> Unit
        }
    }

    private fun observeSearchTeacher() {
        state
            .map { it.searchTeacherText }
            .distinctUntilChanged()
            .debounce(300L)
            .onEach { text ->
                if (text.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchTeacher(text)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchTeacher(teacherName: String) = viewModelScope.launch {

        _state.update { it.copy(
            isLoading = true,
            errorMessage = null
        ) }

        delay(1500)

        when (val result = getTeacherListByNameCase(teacherName)) {

            is CaseResult.Error ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = result.message,
                    teacherList = emptyList()
                ) }

            is CaseResult.Success<List<String>> ->
                _state.update { it.copy(
                    isLoading = false,
                    teacherList = result.data
                ) }
        }
    }
}