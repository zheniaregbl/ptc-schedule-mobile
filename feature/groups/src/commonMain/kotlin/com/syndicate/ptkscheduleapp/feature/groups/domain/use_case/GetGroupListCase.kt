package com.syndicate.ptkscheduleapp.feature.groups.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.groups.domain.repository.GroupRepository

internal class GetGroupListCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(course: Int): CaseResult<List<String>> {
        return when (val response = groupRepository.getGroupList(course)) {
            is ApiResponse.Failure.Error ->
                CaseResult.Error("Ошибка ${response.statusCode.code} при получении групп")
            is ApiResponse.Failure.Exception ->
                CaseResult.Error("Ошибка при попытке получения групп")
            is ApiResponse.Success<List<String>> ->
                CaseResult.Success(response.data)
        }
    }
}