package com.syndicate.ptkscheduleapp.di

import com.syndicate.ptkscheduleapp.core.di.coreModule
import com.syndicate.ptkscheduleapp.core.di.networkModule
import com.syndicate.ptkscheduleapp.feature.groups.di.featureGroupsModule
import com.syndicate.ptkscheduleapp.feature.schedule.di.featureScheduleModule
import com.syndicate.ptkscheduleapp.feature.splash.di.featureSplashModule
import com.syndicate.ptkscheduleapp.feature.teacher.di.featureTeacherModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null,
    platformModules: List<Module> = emptyList()
) {
    startKoin {
        config?.invoke(this)
        modules(
            coreModule,
            networkModule,
            featureSplashModule,
            featureGroupsModule,
            featureTeacherModule,
            featureScheduleModule
        )
        modules(platformModules)
    }
}