package com.syndicate.ptkscheduleapp.feature.role.di

import com.syndicate.ptkscheduleapp.feature.role.presentation.RoleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureRoleModule = module {
    factoryOf(::RoleViewModel)
}