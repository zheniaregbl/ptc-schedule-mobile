package com.syndicate.ptkscheduleapp.widget.presentation

internal sealed interface WidgetAction {
    data object UpdateWidgetSchedule : WidgetAction
}