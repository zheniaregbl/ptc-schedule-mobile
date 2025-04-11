package com.syndicate.ptkscheduleapp.feature.teacher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.FilterTeacherListCase
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.GetTeacherListCase
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
    private val getTeacherListCase: GetTeacherListCase,
    private val filterTeacherListCase: FilterTeacherListCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(TeacherListState())
    val state = _state
        .onStart {
            getTeacherList()
            observeSearchTeacher()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(30_000L),
            _state.value
        )

    fun onAction(action: TeacherListAction) {

        when (action) {

            TeacherListAction.OnUpdateTeacherList -> getTeacherList()

            is TeacherListAction.OnSearchTeacherChange ->
                _state.update { it.copy(
                    searchTeacherText = action.teacherName
                ) }

            is TeacherListAction.OnSelectTeacherList -> selectTeacher(action.teacher)
        }
    }

    private fun observeSearchTeacher() {
        state
            .map { it.searchTeacherText }
            .distinctUntilChanged()
            .debounce(200L)
            .onEach { text ->
                searchJob?.cancel()
                searchJob = filterTeacherList(text)
            }
            .launchIn(viewModelScope)
    }

    private fun filterTeacherList(teacherName: String) = viewModelScope.launch {
        _state.update { it.copy(
            filterTeacherList = filterTeacherListCase(
                it.teacherList,
                teacherName
            )
        ) }
    }

    private fun getTeacherList() = viewModelScope.launch {

        _state.update { it.copy(
            isLoading = true,
            errorMessage = null
        ) }

        delay(300)

        when (val result = getTeacherListCase()) {
            is CaseResult.Error ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = result.message
                ) }
            is CaseResult.Success<List<String>> ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = null,
                    teacherList = result.data,
                    filterTeacherList = result.data
                ) }
        }
    }

    private fun selectTeacher(teacher: String) = viewModelScope.launch {

        preferencesRepository.saveRole(UserRole.TEACHER)
        preferencesRepository.saveTeacher(teacher)

        preferencesRepository.userTeacher
            .collect { userTeacher ->
                _state.update { it.copy(selectedTeacher = userTeacher) }
            }
    }
}