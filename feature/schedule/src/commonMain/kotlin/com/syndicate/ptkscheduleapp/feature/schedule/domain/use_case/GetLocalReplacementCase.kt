package com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case

import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal class GetLocalReplacementCase(
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke(userGroup: String): List<ReplacementItem>? {

        preferencesRepository.getLocalReplacement()?.let { replacementString ->
            return ScheduleUtil.getReplacementFromJson(
                Json.decodeFromString<JsonObject>(replacementString),
                userGroup
            )
        }

        return null
    }
}