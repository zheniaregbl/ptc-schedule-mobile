package com.syndicate.ptkscheduleapp

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.androidPlatformModules
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.feature.groups.di.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.onboarding.di.featureOnboardingScreenModule
import com.syndicate.ptkscheduleapp.feature.role.di.featureRoleScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.di.featureScheduleScreenModule
import com.syndicate.ptkscheduleapp.feature.teacher.di.featureTeacherScreenModule
import org.koin.android.ext.koin.androidContext

class ScheduleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            featureOnboardingScreenModule()
            featureRoleScreenModule()
            featureTeacherScreenModule()
            featureGroupsScreenModule()
            featureScheduleScreenModule()
        }

        initKoin(
            config = { androidContext(this@ScheduleApplication) },
            platformModules = androidPlatformModules(this@ScheduleApplication)
        )
    }
}