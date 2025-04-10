package com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.teacher.domain.repository.TeacherRepository

internal class GetTeacherListCase(
    private val teacherRepository: TeacherRepository
) {
    suspend operator fun invoke(): CaseResult<List<String>> {
        return when (val response = teacherRepository.getTeacherList()) {
            is ApiResponse.Failure.Error ->
                CaseResult.Error("Ошибка ${response.statusCode.code} при получении списка преподавателей")
            is ApiResponse.Failure.Exception ->
                CaseResult.Error("Ошибка при получении списка преподавателей")
            is ApiResponse.Success<List<String>> ->
                CaseResult.Success(response.data)
        }
    }
}