package com.syndicate.ptkscheduleapp.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.update.UpdateChecker
import com.syndicate.ptkscheduleapp.core.update.UpdateInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val updateChecker: UpdateChecker
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    private val _updateState: MutableStateFlow<UpdateInfo?> = MutableStateFlow(null)
    val updateState = _updateState.asStateFlow()

    init {

        viewModelScope.launch {
            preferencesRepository.userThemeMode.collect { theme ->
                _state.update { it.copy(themeMode = theme) }
            }
        }

        viewModelScope.launch {
            _updateState.update { updateChecker.checkUpdate() }
        }
    }
}