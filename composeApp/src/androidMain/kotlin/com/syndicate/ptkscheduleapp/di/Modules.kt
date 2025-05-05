package com.syndicate.ptkscheduleapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.syndicate.ptkscheduleapp.core.datastore.createDataStore
import com.syndicate.ptkscheduleapp.core.di.androidCoreModule
import com.syndicate.ptkscheduleapp.core.presentation.PlatformBatterySettings
import com.syndicate.ptkscheduleapp.widget.di.widgetModule
import com.syndicate.ptkscheduleapp.widget.presentation.WidgetManager
import org.koin.dsl.module

fun androidModule(context: Context) = module {
    single<DataStore<Preferences>> { createDataStore(context) }
    single<PlatformBatterySettings> { PlatformBatterySettings(context) }
    single<WidgetManager> { WidgetManager(context) }
}

fun androidPlatformModules(context: Context) =
    listOf(
        androidModule(context),
        androidCoreModule,
        widgetModule
    )