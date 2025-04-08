package com.syndicate.ptkscheduleapp.feature.teacher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

internal class TeacherListViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(TeacherListState())
    val state = _state
        .onStart {  }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(30_000L),
            _state.value
        )
}