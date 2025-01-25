package com.syndicate.ptkscheduleapp.feature.schedule.data.mapper

import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.GroupScheduleUpdateInfoDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.ScheduleInfoDTO
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.GroupScheduleUpdateInfo
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ScheduleInfo

internal fun GroupScheduleUpdateInfoDTO.toModel() = GroupScheduleUpdateInfo(
    group = group,
    lastUpdateTime = this.lastUpdateTime
)

internal fun ScheduleInfoDTO.toModel() = ScheduleInfo(
    isUpperWeek = isUpperWeek,
    lastScheduleUpdateTime = this.lastScheduleUpdateTime,
    lastReplacementUpdateTime = this.lastReplacementUpdateTime,
    groupInfo = groupInfo.map { it.toModel() }
)