package com.syndicate.ptkscheduleapp.core.domain.model

data class TokenBodyModel(
    val keyId: String,
    val timestamp: String,
    val signature: String
)
