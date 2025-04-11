package com.syndicate.ptkscheduleapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.syndicate.ptkscheduleapp.core.data.datastore.createDataStore
import com.syndicate.ptkscheduleapp.core.presentation.PlatformBatterySettings
import com.syndicate.ptkscheduleapp.widget.di.widgetModule
import org.koin.dsl.module

fun androidModule(context: Context) = module {
    single<DataStore<Preferences>> { createDataStore(context) }
    single<PlatformBatterySettings> { PlatformBatterySettings(context) }
}

fun androidPlatformModules(context: Context) = listOf(androidModule(context), widgetModule)