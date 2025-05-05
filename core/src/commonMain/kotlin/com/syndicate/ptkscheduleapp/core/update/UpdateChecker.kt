package com.syndicate.ptkscheduleapp.core.update

internal expect class UpdateChecker {
    suspend fun checkUpdate(): UpdateInfo?
}