package com.syndicate.ptkscheduleapp.feature.schedule.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleScreen
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetWeekTypeBySelectedDate
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf

val featureScheduleModule = module {
    singleOf(::GetWeekTypeBySelectedDate)
    factoryOf(::ScheduleViewModel)
}

val featureScheduleScreenModule = screenModule {
    register<SharedScreen.ScheduleScreen> { ScheduleScreen() }
}