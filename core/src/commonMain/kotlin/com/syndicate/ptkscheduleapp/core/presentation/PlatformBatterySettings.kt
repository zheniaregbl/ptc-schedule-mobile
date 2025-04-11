package com.syndicate.ptkscheduleapp.core.presentation

expect class PlatformBatterySettings {
    fun requestBackgroundPermission()
    fun isBackgroundPermissionGranted(): Boolean
}