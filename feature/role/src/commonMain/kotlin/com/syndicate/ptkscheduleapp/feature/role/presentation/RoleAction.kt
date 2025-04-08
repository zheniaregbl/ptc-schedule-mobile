package com.syndicate.ptkscheduleapp.feature.role.presentation

sealed interface RoleAction {
    data object NavigateToGroupSelection : RoleAction
}