package com.syndicate.ptkscheduleapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.syndicate.ptkscheduleapp.core.datastore.createDataStore
import com.syndicate.ptkscheduleapp.core.presentation.PlatformBatterySettings
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> { createDataStore() }
    single<PlatformBatterySettings> { PlatformBatterySettings() }
}

val iosPlatformModules = listOf(iosModule)