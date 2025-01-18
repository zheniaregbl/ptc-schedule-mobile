package com.syndicate.ptkscheduleapp.feature.groups.data.mapper

import com.syndicate.ptkscheduleapp.feature.groups.data.dto.AllGroupsDTO
import com.syndicate.ptkscheduleapp.feature.groups.domain.model.AllGroups

internal fun AllGroupsDTO.toModel() =
    AllGroups(firstCourse, secondCourse, thirdCourse, fourthCourse)