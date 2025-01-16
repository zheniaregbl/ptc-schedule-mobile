package com.syndicate.ptkscheduleapp

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.navigation.featureSearchModule
import org.koin.android.ext.koin.androidContext

class ScheduleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry { featureSearchModule() }

        initKoin(config = { androidContext(this@ScheduleApplication) })
    }
}