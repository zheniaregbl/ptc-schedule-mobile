package com.syndicate.ptkscheduleapp.core.update

expect class UpdateChecker {
    suspend fun checkUpdate(): UpdateInfo?
}