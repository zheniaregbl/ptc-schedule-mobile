package com.syndicate.ptkscheduleapp

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.androidPlatformModules
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.feature.groups.navigation.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.onboarding.navigation.featureOnboardingScreenModule
import com.syndicate.ptkscheduleapp.feature.role.navigation.featureRoleScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.navigation.featureScheduleScreenModule
import com.syndicate.ptkscheduleapp.feature.teacher.navigation.featureTeacherScreenModule
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