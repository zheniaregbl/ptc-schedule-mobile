package com.syndicate.ptkscheduleapp.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ThemeViewModel : ViewModel() {

    private val _state = MutableStateFlow(ThemeState())
    val state = _state.asStateFlow()

    fun onAction(action: ThemeAction) {

        when (action) {

            is ThemeAction.ChangeTheme -> {
                _state.update { it.copy(
                    themeMode = action.themeMode,
                    animationOffset = action.offset
                ) }
            }

            ThemeAction.LockChangeTheme -> _state.update { it.copy(enableChangeTheme = false) }

            ThemeAction.UnlockChangeTheme -> _state.update { it.copy(enableChangeTheme = true) }
        }
    }
}