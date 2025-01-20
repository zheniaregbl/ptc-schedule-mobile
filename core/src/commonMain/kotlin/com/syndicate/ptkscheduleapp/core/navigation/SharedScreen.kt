package com.syndicate.ptkscheduleapp.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    data object GroupScreen : SharedScreen()
    data object ScheduleScreen : SharedScreen()
}