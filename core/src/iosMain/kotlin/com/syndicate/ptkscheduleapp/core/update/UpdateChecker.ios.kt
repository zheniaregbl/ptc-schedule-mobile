package com.syndicate.ptkscheduleapp.core.update

actual class UpdateChecker() {
    actual suspend fun checkUpdate(): UpdateInfo? {
        return null
    }
}