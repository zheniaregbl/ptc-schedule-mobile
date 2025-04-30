package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier

internal class GetUserIdentifierCase(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(): CaseResult<UserIdentifier> {

        return when (val userRole = preferencesRepository.getUserRole()) {

            null -> CaseResult.Error("Error init user role")

            else -> {
                val userIdentifier = when (userRole) {
                    UserRole.STUDENT -> UserIdentifier.Student(preferencesRepository.getUserGroup())
                    UserRole.TEACHER -> UserIdentifier.Teacher(preferencesRepository.getUserTeacher())
                }
                CaseResult.Success(userIdentifier)
            }
        }
    }
}