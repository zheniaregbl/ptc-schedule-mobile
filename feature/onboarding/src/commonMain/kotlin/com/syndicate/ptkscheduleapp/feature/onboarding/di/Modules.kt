package com.syndicate.ptkscheduleapp.feature.onboarding.di

import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.OnboardingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureOnboardingModule = module {
    factoryOf(::OnboardingViewModel)
}