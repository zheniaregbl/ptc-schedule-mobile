package com.syndicate.ptkscheduleapp.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState

internal class ScheduleWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = ScheduleWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    private suspend fun fetchSchedule(context: Context) {

        val glanceIds = GlanceAppWidgetManager(context)
            .getGlanceIds(ScheduleWidget::class.java)

        glanceIds.forEach { id ->

            updateAppWidgetState(context, id) { state ->

            }

            glanceAppWidget.update(context, id)
        }
    }

    private suspend fun changeTheme(context: Context) {

        val glanceIds = GlanceAppWidgetManager(context)
            .getGlanceIds(ScheduleWidget::class.java)

        glanceIds.forEach { id ->

            updateAppWidgetState(context, id) { state ->
                val previousTheme = state[AlternativeTheme] == true
                state[AlternativeTheme] = !previousTheme
            }

            glanceAppWidget.update(context, id)
        }
    }

    companion object {
        val UpdateTime = stringPreferencesKey("update_time")
        val GroupNumber = stringPreferencesKey("group_number")
        val WidgetSchedule = stringPreferencesKey("widget_schedule")
        val IsLoading = booleanPreferencesKey("is_loading")
        val AlternativeTheme = booleanPreferencesKey("alternative_theme")

        const val UPDATE_ACTION = "update_action"
        const val CHANGE_THEME_ACTION = "change_theme_action"
    }
}