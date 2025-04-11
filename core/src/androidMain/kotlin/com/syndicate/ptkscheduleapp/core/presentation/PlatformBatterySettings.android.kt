package com.syndicate.ptkscheduleapp.core.presentation

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import androidx.core.net.toUri

actual class PlatformBatterySettings(
    private val context: Context
) {

    actual fun requestBackgroundPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = "package:${context.packageName}".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    actual fun isBackgroundPermissionGranted(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val value = powerManager.isIgnoringBatteryOptimizations(context.packageName)
        return value
    }
}