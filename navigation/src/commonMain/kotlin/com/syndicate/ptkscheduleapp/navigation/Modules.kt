package com.syndicate.ptkscheduleapp.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.feature.groups.presentation.GroupScreen

val featureSearchModule = screenModule {
    register<SharedScreen.GroupScreen> { GroupScreen() }
}