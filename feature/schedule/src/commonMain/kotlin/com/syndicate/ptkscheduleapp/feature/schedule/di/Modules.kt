package com.syndicate.ptkscheduleapp.feature.schedule.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultPreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleScreen
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.schedule.data.network.RemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.feature.schedule.data.network.KtorRemoteScheduleDataSource
import com.syndicate.ptkscheduleapp.feature.schedule.data.repository.DefaultScheduleRepository
import com.syndicate.ptkscheduleapp.feature.schedule.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val featureScheduleModule = module {
    singleOf(::KtorRemoteScheduleDataSource).bind<RemoteScheduleDataSource>()
    singleOf(::DefaultScheduleRepository).bind<ScheduleRepository>()
    singleOf(::DefaultPreferencesRepository).bind<PreferencesRepository>()

    viewModelOf(::ScheduleViewModel)
}

val featureScheduleScreenModule = screenModule {
    register<SharedScreen.ScheduleScreen> { ScheduleScreen() }
}