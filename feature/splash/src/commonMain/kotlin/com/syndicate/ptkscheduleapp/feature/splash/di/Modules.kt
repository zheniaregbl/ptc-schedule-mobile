package com.syndicate.ptkscheduleapp.feature.splash.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultSettingsRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.SettingsRepository
import com.syndicate.ptkscheduleapp.feature.splash.presentation.LaunchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val featureSplashModule = module {
    singleOf(::DefaultSettingsRepository).bind<SettingsRepository>()
    viewModelOf(::LaunchViewModel)
}