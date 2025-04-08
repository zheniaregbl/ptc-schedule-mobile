package com.syndicate.ptkscheduleapp.feature.teacher.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.teacher.data.network.RemoteTeacherDataSource
import com.syndicate.ptkscheduleapp.feature.teacher.data.network.KtorRemoteTeacherDataSource
import com.syndicate.ptkscheduleapp.feature.teacher.domain.repository.TeacherRepository
import com.syndicate.ptkscheduleapp.feature.teacher.data.repository.DefaultTeacherRepository
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.GetTeacherListByNameCase
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherListViewModel
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureTeacherModule = module {
    singleOf(::KtorRemoteTeacherDataSource).bind<RemoteTeacherDataSource>()
    singleOf(::DefaultTeacherRepository).bind<TeacherRepository>()

    singleOf(::GetTeacherListByNameCase)

    factoryOf(::TeacherListViewModel)
}

val featureTeacherScreenModule = screenModule {
    register<SharedScreen.TeacherScreen> { TeacherScreen() }
}