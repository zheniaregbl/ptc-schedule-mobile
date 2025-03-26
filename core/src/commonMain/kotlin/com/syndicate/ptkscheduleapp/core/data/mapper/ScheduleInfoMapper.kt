package com.syndicate.ptkscheduleapp.core.data.mapper

import com.syndicate.ptkscheduleapp.core.data.dto.GroupScheduleUpdateInfoDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ScheduleInfoDTO
import com.syndicate.ptkscheduleapp.core.domain.model.GroupScheduleUpdateInfo
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo

fun GroupScheduleUpdateInfoDTO.toModel() = GroupScheduleUpdateInfo(
    group = group,
    lastUpdateTime = this.lastUpdateTime
)

fun ScheduleInfoDTO.toModel() = ScheduleInfo(
    isUpperWeek = isUpperWeek,
    lastScheduleUpdateTime = this.lastScheduleUpdateTime,
    lastReplacementUpdateTime = this.lastReplacementUpdateTime,
    groupInfo = groupInfo.map { it.toModel() }
)