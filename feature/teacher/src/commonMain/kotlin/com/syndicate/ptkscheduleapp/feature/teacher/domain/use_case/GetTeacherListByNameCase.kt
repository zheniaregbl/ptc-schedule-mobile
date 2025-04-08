package com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.teacher.domain.repository.TeacherRepository

internal class GetTeacherListByNameCase(
    private val teacherRepository: TeacherRepository
) {
    suspend operator fun invoke(teacherName: String): CaseResult<List<String>> {
        return when (val response = teacherRepository.getTeacherList()) {
            is ApiResponse.Failure.Error ->
                CaseResult.Error("Ошибка ${response.statusCode.code} при получении списка преподавателей")
            is ApiResponse.Failure.Exception ->
                CaseResult.Error("Ошибка при получении списка преподавателей")
            is ApiResponse.Success<List<String>> -> {
                val resultList = response.data.filter { it.startsWith(teacherName, true) }
                if (resultList.isEmpty())
                    CaseResult.Error("Не удалось найти преподавателя")
                else
                    CaseResult.Success(resultList)
            }
        }
    }
}