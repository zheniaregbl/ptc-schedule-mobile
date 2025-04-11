package com.syndicate.ptkscheduleapp.feature.groups.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.groups.presentation.GroupScreen

val featureGroupsScreenModule = screenModule {
    register<SharedScreen.GroupScreen> { GroupScreen() }
}