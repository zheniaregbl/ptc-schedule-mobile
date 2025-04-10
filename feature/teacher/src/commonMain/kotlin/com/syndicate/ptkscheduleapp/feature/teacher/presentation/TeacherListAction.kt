package com.syndicate.ptkscheduleapp.feature.teacher.presentation

internal sealed interface TeacherListAction {
    data object OnUpdateTeacherList : TeacherListAction
    data class OnSelectTeacherList(val teacher: String) : TeacherListAction
    data class OnSearchTeacherChange(val teacherName: String) : TeacherListAction
}