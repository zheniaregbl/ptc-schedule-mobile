package com.syndicate.ptkscheduleapp.widget.di

import com.syndicate.ptkscheduleapp.widget.presentation.WidgetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val widgetModule = module {
    viewModelOf(::WidgetViewModel)
}