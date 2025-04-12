package com.syndicate.ptkscheduleapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.syndicate.ptkscheduleapp.core.datastore.createDataStore
import com.syndicate.ptkscheduleapp.core.presentation.PlatformBatterySettings
import com.syndicate.ptkscheduleapp.widget.presentation.WidgetManager
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> { createDataStore() }
    single<PlatformBatterySettings> { PlatformBatterySettings() }
    single<WidgetManager> { WidgetManager() }
}

val iosPlatformModules = listOf(iosModule)