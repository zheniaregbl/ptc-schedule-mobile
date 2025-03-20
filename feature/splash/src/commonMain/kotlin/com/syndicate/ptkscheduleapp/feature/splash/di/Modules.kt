package com.syndicate.ptkscheduleapp.feature.splash.di

import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.splash.presentation.LaunchViewModel
import org.koin.core.module.dsl.viewModelOf

val featureSplashModule = module {
    viewModelOf(::LaunchViewModel)
}