package com.syndicate.ptkscheduleapp.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.widget.presentation.ScheduleWidgetUI
import kotlinx.serialization.json.Json

internal class ScheduleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) = provideContent {

        val isLoading = currentState<Boolean>(ScheduleWidgetReceiver.IsLoading) == true
        val isAlternativeTheme = currentState<Boolean>(ScheduleWidgetReceiver.AlternativeTheme) == true
        val updateTime = currentState<String>(ScheduleWidgetReceiver.UpdateTime) ?: ""
        val groupNumber = currentState<String>(ScheduleWidgetReceiver.GroupNumber) ?: ""
        val stringSchedule = currentState<String>(ScheduleWidgetReceiver.WidgetSchedule) ?: ""

        val widgetSchedule = if (stringSchedule.isNotBlank()) {
            Json.decodeFromString<List<List<PairDTO>>>(stringSchedule)
                .map { weekSchedule -> weekSchedule.map { it.toModel() } }
        } else emptyList()

        ScheduleWidgetUI(
            widgetSchedule = widgetSchedule,
            isAlternativeTheme = isAlternativeTheme,
            isLoading = isLoading,
            updateTime = updateTime,
            groupNumber = groupNumber
        )
    }
}