package com.syndicate.ptkscheduleapp.feature.teacher.domain.repository

import com.skydoves.sandwich.ApiResponse

internal interface TeacherRepository {
    suspend fun getTeacherList(): ApiResponse<List<String>>
}