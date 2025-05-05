package com.syndicate.ptkscheduleapp.core.domain.model

internal data class AppVersionDataModel(
    val versionName: String,
    val versionCode: Int,
    val status: String,
    val info: String
)
