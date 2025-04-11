package com.syndicate.ptkscheduleapp.feature.onboarding.presentation

internal sealed interface OnboardingAction {
    data object CheckBackgroundPermission : OnboardingAction
    data object OnRequestBackgroundPermission : OnboardingAction
    data object NavigateToRoleSelection : OnboardingAction
}