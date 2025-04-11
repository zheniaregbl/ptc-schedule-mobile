package com.syndicate.ptkscheduleapp.feature.role.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.role.presentation.RoleScreen

val featureRoleScreenModule = screenModule {
    register<SharedScreen.RoleScreen> { RoleScreen() }
}