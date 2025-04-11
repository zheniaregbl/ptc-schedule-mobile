package com.syndicate.ptkscheduleapp.feature.onboarding.presentation

internal sealed interface OnboardingAction {
    data object NavigateToRoleSelection : OnboardingAction
}