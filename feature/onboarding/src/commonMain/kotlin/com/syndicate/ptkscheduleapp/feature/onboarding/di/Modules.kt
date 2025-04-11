package com.syndicate.ptkscheduleapp.feature.onboarding.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.OnboardingViewModel
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.OnboardingScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureOnboardingModule = module {
    factoryOf(::OnboardingViewModel)
}

val featureOnboardingScreenModule = screenModule {
    register<SharedScreen.OnboardingScreen> { OnboardingScreen() }
}