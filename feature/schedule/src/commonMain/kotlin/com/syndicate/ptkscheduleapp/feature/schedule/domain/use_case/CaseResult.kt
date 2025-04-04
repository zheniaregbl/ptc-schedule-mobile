package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

internal sealed class CaseResult<out T> {
    data class Success<T>(val data: T) : CaseResult<T>()
    data class Error(val message: String) : CaseResult<Nothing>()
}