package com.syndicate.ptkscheduleapp.feature.role.presentation

internal sealed interface RoleAction {
    data object NavigateToGroupSelection : RoleAction
    data object NavigateToTeacherSelection : RoleAction
}