package com.syndicate.ptkscheduleapp.di

import com.syndicate.ptkscheduleapp.core.di.networkModule
import com.syndicate.ptkscheduleapp.feature.groups.di.groupModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null,
    platformModules: List<Module> = emptyList()
) {
    startKoin {
        config?.invoke(this)
        modules(networkModule, groupModule)
        modules(platformModules)
    }
}