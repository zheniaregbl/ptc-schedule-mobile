package com.syndicate.ptkscheduleapp.feature.teacher.data.network

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.feature.teacher.data.dto.TeacherResponseDTO

internal interface RemoteTeacherDataSource {
    suspend fun getTeacherList(): ApiResponse<TeacherResponseDTO>
}