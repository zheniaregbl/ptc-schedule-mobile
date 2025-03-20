package com.syndicate.ptkscheduleapp.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.syndicate.ptkscheduleapp.widget.presentation.ScheduleWidgetUI

internal class ScheduleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) = provideContent {
        ScheduleWidgetUI()
    }
}