package com.syndicate.ptkscheduleapp.feature.schedule.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleScreen

val featureScheduleScreenModule = screenModule {
    register<SharedScreen.ScheduleScreen> { ScheduleScreen() }
}