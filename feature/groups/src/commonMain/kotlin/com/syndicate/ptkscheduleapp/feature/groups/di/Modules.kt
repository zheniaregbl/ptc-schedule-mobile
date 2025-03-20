package com.syndicate.ptkscheduleapp.feature.groups.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.groups.presentation.GroupViewModel
import com.syndicate.ptkscheduleapp.feature.groups.data.network.KtorRemoteGroupDataSource
import com.syndicate.ptkscheduleapp.feature.groups.data.network.RemoteGroupDataSource
import com.syndicate.ptkscheduleapp.feature.groups.data.repository.DefaultGroupRepository
import com.syndicate.ptkscheduleapp.feature.groups.domain.repository.GroupRepository
import com.syndicate.ptkscheduleapp.feature.groups.presentation.GroupScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

val featureGroupsModule = module {
    singleOf(::KtorRemoteGroupDataSource).bind<RemoteGroupDataSource>()
    singleOf(::DefaultGroupRepository).bind<GroupRepository>()

    factoryOf(::GroupViewModel)
}

val featureGroupsScreenModule = screenModule {
    register<SharedScreen.GroupScreen> { GroupScreen(it.themeMode) }
}