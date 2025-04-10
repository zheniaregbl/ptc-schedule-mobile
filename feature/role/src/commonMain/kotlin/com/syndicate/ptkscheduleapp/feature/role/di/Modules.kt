package com.syndicate.ptkscheduleapp.feature.role.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.role.presentation.RoleScreen
import com.syndicate.ptkscheduleapp.feature.role.presentation.RoleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureRoleModule = module {
    factoryOf(::RoleViewModel)
}

val featureRoleScreenModule = screenModule {
    register<SharedScreen.RoleScreen> { RoleScreen() }
}