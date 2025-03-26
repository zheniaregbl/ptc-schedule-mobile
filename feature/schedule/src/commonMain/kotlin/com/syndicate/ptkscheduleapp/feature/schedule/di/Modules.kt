package com.syndicate.ptkscheduleapp.feature.schedule.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleScreen
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleViewModel
import org.koin.core.module.dsl.factoryOf

val featureScheduleModule = module {
    factoryOf(::ScheduleViewModel)
}

val featureScheduleScreenModule = screenModule {
    register<SharedScreen.ScheduleScreen> { ScheduleScreen() }
}