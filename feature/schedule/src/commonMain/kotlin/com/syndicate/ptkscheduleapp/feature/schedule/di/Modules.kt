package com.syndicate.ptkscheduleapp.feature.schedule.di

import org.koin.dsl.module
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetWeekTypeBySelectedDateCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetScheduleInfoCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetReplacementCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetScheduleCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalWeekTypeCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalReplacementCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalScheduleCase
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf

val featureScheduleModule = module {
    singleOf(::GetScheduleInfoCase)
    singleOf(::GetReplacementCase)
    singleOf(::GetScheduleCase)
    singleOf(::GetLocalWeekTypeCase)
    singleOf(::GetLocalReplacementCase)
    singleOf(::GetLocalScheduleCase)
    singleOf(::GetWeekTypeBySelectedDateCase)
    factoryOf(::ScheduleViewModel)
}