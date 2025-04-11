package com.syndicate.ptkscheduleapp.core.presentation

actual class PlatformBatterySettings() {
    actual fun requestBackgroundPermission() {}
    actual fun isBackgroundPermissionGranted(): Boolean {
        return true
    }
}