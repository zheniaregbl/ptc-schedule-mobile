package com.syndicate.ptkscheduleapp.feature.teacher.presentation

internal sealed interface TeacherListAction {
    data class OnSearchTeacherListChange(val teacherName: String) : TeacherListAction
    data class OnSelectTeacherList(val teacher: String) : TeacherListAction
}