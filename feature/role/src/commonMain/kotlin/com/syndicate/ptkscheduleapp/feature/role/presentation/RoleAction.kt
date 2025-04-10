package com.syndicate.ptkscheduleapp.feature.role.presentation

internal sealed interface RoleAction {
    data class OnSelectRole(val roleIndex: Int) : RoleAction
    data object NavigateToGroupSelection : RoleAction
    data object NavigateToTeacherSelection : RoleAction
}