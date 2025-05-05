package com.syndicate.ptkscheduleapp.core.di

import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.core.data.network.KtorRemoteRuStoreDataSource
import com.syndicate.ptkscheduleapp.core.data.network.RemoteRuStoreDataSource
import com.syndicate.ptkscheduleapp.core.domain.repository.RuStoreRepository
import com.syndicate.ptkscheduleapp.core.data.repository.DefaultRuStoreRepository
import com.syndicate.ptkscheduleapp.core.domain.use_case.GetLastAppVersion
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val androidCoreModule = module {
    singleOf(::KtorRemoteRuStoreDataSource).bind<RemoteRuStoreDataSource>()
    singleOf(::DefaultRuStoreRepository).bind<RuStoreRepository>()
    singleOf(::GetLastAppVersion)
}