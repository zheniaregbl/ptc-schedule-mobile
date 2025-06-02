package com.syndicate.ptkscheduleapp.widget.domain.use_case

import com.skydoves.sandwich.ApiResponse
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.model.UserRole
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class GetReplacementCase(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(
        dateStart: String = "",
        dateEnd: String = ""
    ): List<ReplacementItem>? {

        val userRole = preferencesRepository.getUserRole() ?: return null

        val userIdentifier = when (userRole) {
            UserRole.STUDENT -> UserIdentifier.Student(preferencesRepository.getUserGroup())
            UserRole.TEACHER -> UserIdentifier.Teacher(preferencesRepository.getUserTeacher())
        }

        return when (val response = scheduleRepository.getReplacement(dateStart, dateEnd, userIdentifier)) {

            is ApiResponse.Success<JsonObject> -> {
                preferencesRepository.saveLocalReplacement(response.data.toString())
                when (userIdentifier) {
                    is UserIdentifier.Student ->
                        ScheduleUtil.getReplacementFromJsonForStudent(response.data, userIdentifier.group)
                    is UserIdentifier.Teacher ->
                        ScheduleUtil.getReplacementFromJsonForTeacher(response.data, userIdentifier.name)
                }
            }

            else -> {
                preferencesRepository.getLocalReplacement()?.let { replacementString ->
                    return when (userIdentifier) {
                        is UserIdentifier.Student ->
                            ScheduleUtil.getReplacementFromJsonForStudent(
                                Json.decodeFromString<JsonObject>(replacementString),
                                userIdentifier.group
                            )
                        is UserIdentifier.Teacher ->
                            ScheduleUtil.getReplacementFromJsonForTeacher(
                                Json.decodeFromString<JsonObject>(replacementString),
                                userIdentifier.name
                            )
                    }
                }
                return null
            }
        }
    }
}