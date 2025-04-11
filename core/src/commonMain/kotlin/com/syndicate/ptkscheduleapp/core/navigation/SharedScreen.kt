package com.syndicate.ptkscheduleapp.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    data object OnboardingScreen : SharedScreen()
    data object RoleScreen : SharedScreen()
    data object TeacherScreen : SharedScreen()
    data object GroupScreen : SharedScreen()
    data object ScheduleScreen : SharedScreen()
}