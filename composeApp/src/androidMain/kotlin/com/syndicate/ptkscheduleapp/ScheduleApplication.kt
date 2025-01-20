package com.syndicate.ptkscheduleapp

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.feature.groups.di.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.di.featureScheduleScreenModule
import org.koin.android.ext.koin.androidContext

class ScheduleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            featureGroupsScreenModule()
            featureScheduleScreenModule()
        }

        initKoin(config = { androidContext(this@ScheduleApplication) })
    }
}