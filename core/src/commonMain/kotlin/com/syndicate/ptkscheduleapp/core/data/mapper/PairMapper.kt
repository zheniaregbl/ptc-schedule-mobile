package com.syndicate.ptkscheduleapp.core.data.mapper

import com.syndicate.ptkscheduleapp.core.data.dto.GroupDTO
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.dto.ReplacementInfoDTO
import com.syndicate.ptkscheduleapp.core.data.dto.RoomDTO
import com.syndicate.ptkscheduleapp.core.data.dto.SubjectDTO
import com.syndicate.ptkscheduleapp.core.data.dto.TeacherDTO
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.PreviousPairInfo

fun PairDTO.toModel() = PairItem(
    dayOfWeek = dayOfWeek,
    isUpper = isUpper,
    pairNumber = pairNumber,
    subject = subject.name,
    group = group.name,
    place = room.place,
    room = room.number,
    teacher = teacher.fullName,
    subgroupNumber = subgroupNumber,
    time = time,
    previousPairNumber = replacementInfoDTO?.previousPairNumber ?: -1,
    isNewPair = replacementInfoDTO?.isNewPair ?: false,
    swapPair = replacementInfoDTO?.swapPair ?: false,
    isReplacement = replacementInfoDTO?.isReplacement ?: false,
    previousPairInfo = if (originalPair != null)
        PreviousPairInfo(
            isUpper = originalPair.isUpper,
            subject = originalPair.subject.name,
            group = originalPair.group.name,
            place = originalPair.room.place,
            room = originalPair.room.number,
            teacher = originalPair.teacher.fullName,
            subgroupNumber = originalPair.subgroupNumber,
            time = originalPair.time
        )
    else null
)

fun PairItem.toDTO() = PairDTO(
    dayOfWeek = dayOfWeek,
    isUpper = isUpper,
    pairNumber = pairNumber,
    subject = SubjectDTO(subject),
    group = GroupDTO(group),
    room = RoomDTO(room, place),
    teacher = TeacherDTO(teacher),
    subgroupNumber = subgroupNumber,
    time = time
)

fun PairItem.toLocalDTO() = PairDTO(
    dayOfWeek = dayOfWeek,
    isUpper = isUpper,
    pairNumber = pairNumber,
    subject = SubjectDTO(subject),
    group = GroupDTO(group),
    room = RoomDTO(room, place),
    teacher = TeacherDTO(teacher),
    subgroupNumber = subgroupNumber,
    time = time,
    replacementInfoDTO = ReplacementInfoDTO(
        isReplacement = isReplacement,
        previousPairNumber = previousPairNumber,
        isNewPair = isNewPair,
        swapPair = swapPair
    )
)