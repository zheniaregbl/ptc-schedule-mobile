package com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case

internal class FilterTeacherListCase() {
    operator fun invoke(
        teacherList: List<String>,
        teacherName: String
    ) = teacherList.filter { it.startsWith(teacherName, true) }
}