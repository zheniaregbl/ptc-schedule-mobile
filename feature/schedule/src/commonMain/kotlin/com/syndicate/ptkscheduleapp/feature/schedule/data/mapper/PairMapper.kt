package com.syndicate.ptkscheduleapp.feature.schedule.data.mapper

import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.RoomDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.SubjectDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.TeacherDTO
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem

internal fun PairDTO.toModel() = PairItem(
    dayOfWeek = dayOfWeek,
    isUpper = isUpper,
    pairNumber = pairNumber,
    subject = subject.name,
    place = room.place,
    room = room.number,
    teacher = teacher.fullName,
    subgroupNumber = subgroupNumber,
    time = time
)

internal fun PairItem.toDTO() = PairDTO(
    dayOfWeek = dayOfWeek,
    isUpper = isUpper,
    pairNumber = pairNumber,
    subject = SubjectDTO(subject),
    room = RoomDTO(room, place),
    teacher = TeacherDTO(teacher),
    subgroupNumber = subgroupNumber,
    time = time
)