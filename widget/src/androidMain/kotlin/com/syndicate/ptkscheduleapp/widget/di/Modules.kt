package com.syndicate.ptkscheduleapp.widget.di

import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetWeekTypeCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetReplacementCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetScheduleCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetDailyScheduleCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.SaveWidgetSchedule
import com.syndicate.ptkscheduleapp.widget.presentation.WidgetViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val widgetModule = module {
    singleOf(::GetWeekTypeCase)
    singleOf(::GetReplacementCase)
    singleOf(::GetScheduleCase)
    singleOf(::GetDailyScheduleCase)
    singleOf(::SaveWidgetSchedule)
    viewModelOf(::WidgetViewModel)
}