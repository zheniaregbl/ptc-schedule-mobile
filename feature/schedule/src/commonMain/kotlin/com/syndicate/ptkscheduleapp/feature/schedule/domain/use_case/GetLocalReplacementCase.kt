package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.UserIdentifier
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal class GetLocalReplacementCase(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(userIdentifier: UserIdentifier): List<ReplacementItem>? {

        preferencesRepository.getLocalReplacement()?.let { replacementString ->
            return when (userIdentifier) {
                is UserIdentifier.Student ->
                    ScheduleUtil.getReplacementFromJsonForStudent(
                        Json.decodeFromString<JsonObject>(replacementString),
                        userIdentifier.group
                    )
                is UserIdentifier.Teacher ->
                    // TODO: add fetching replacement for teacher after refactor on backend
                    emptyList()
            }
        }

        return null
    }
}