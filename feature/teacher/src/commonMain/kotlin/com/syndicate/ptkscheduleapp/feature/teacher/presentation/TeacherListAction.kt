package com.syndicate.ptkscheduleapp.feature.teacher.presentation

internal sealed interface TeacherListAction {
    data class OnSearchTeacherChange(val teacherName: String) : TeacherListAction
    data class OnSelectTeacherList(val teacher: String) : TeacherListAction
}