package com.syndicate.ptkscheduleapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.syndicate.ptkscheduleapp.core.data.datastore.createDataStore
import org.koin.dsl.module

val iosModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}

val iosPlatformModules = listOf(iosModule)