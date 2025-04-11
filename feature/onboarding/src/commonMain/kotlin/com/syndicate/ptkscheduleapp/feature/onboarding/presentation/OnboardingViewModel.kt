package com.syndicate.ptkscheduleapp.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.presentation.PlatformBatterySettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class OnboardingViewModel(
    private val platformBatterySettings: PlatformBatterySettings
) : ViewModel() {

    private val _isBackgroundPermissionGranted: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isBackgroundPermissionGranted = _isBackgroundPermissionGranted.asStateFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            OnboardingAction.CheckBackgroundPermission -> checkBackgroundPermission()
            OnboardingAction.OnRequestBackgroundPermission -> requestBackgroundPermission()
            else -> Unit
        }
    }

    private fun checkBackgroundPermission() = viewModelScope.launch {
        _isBackgroundPermissionGranted.update { null }
        delay(10)
        _isBackgroundPermissionGranted.update {
            platformBatterySettings
                .isBackgroundPermissionGranted()
        }
    }

    private fun requestBackgroundPermission() {
        platformBatterySettings.requestBackgroundPermission()
    }
}