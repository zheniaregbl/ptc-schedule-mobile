package com.syndicate.ptkscheduleapp.feature.splash.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultPreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.feature.splash.presentation.LaunchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val featureSplashModule = module {
    singleOf(::DefaultPreferencesRepository).bind<PreferencesRepository>()
    viewModelOf(::LaunchViewModel)
}