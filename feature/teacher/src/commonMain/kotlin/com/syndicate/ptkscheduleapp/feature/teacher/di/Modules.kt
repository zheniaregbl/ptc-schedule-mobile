package com.syndicate.ptkscheduleapp.feature.teacher.di

import com.syndicate.ptkscheduleapp.feature.teacher.data.network.RemoteTeacherDataSource
import com.syndicate.ptkscheduleapp.feature.teacher.data.network.KtorRemoteTeacherDataSource
import com.syndicate.ptkscheduleapp.feature.teacher.domain.repository.TeacherRepository
import com.syndicate.ptkscheduleapp.feature.teacher.data.repository.DefaultTeacherRepository
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.GetTeacherListCase
import com.syndicate.ptkscheduleapp.feature.teacher.domain.use_case.FilterTeacherListCase
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureTeacherModule = module {
    factoryOf(::KtorRemoteTeacherDataSource).bind<RemoteTeacherDataSource>()
    factoryOf(::DefaultTeacherRepository).bind<TeacherRepository>()
    factoryOf(::GetTeacherListCase)
    factoryOf(::FilterTeacherListCase)
    factoryOf(::TeacherListViewModel)
}