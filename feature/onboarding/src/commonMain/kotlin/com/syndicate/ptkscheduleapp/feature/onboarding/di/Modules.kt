package com.syndicate.ptkscheduleapp.feature.onboarding.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.OnboardingScreen

val featureOnboardingScreenModule = screenModule {
    register<SharedScreen.OnboardingScreen> { OnboardingScreen() }
}