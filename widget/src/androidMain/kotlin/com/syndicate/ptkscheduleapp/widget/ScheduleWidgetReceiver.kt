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
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.widget.presentation.WidgetAction
import com.syndicate.ptkscheduleapp.widget.presentation.WidgetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ScheduleWidgetReceiver : GlanceAppWidgetReceiver(), KoinComponent {

    private val widgetViewModel by inject<WidgetViewModel>()
    private val preferencesRepository by inject<PreferencesRepository>()

    override val glanceAppWidget: GlanceAppWidget
        get() = ScheduleWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        CoroutineScope(Dispatchers.IO).launch {
            changeWidgetState(context)
            fetchSchedule(context)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {

            Intent.ACTION_TIMEZONE_CHANGED -> CoroutineScope(Dispatchers.IO).launch {
                changeWidgetState(context)
                fetchSchedule(context)
            }

            UPDATE_ACTION -> CoroutineScope(Dispatchers.IO).launch {
                changeWidgetState(context)
                fetchSchedule(context)
            }

            CHANGE_THEME_ACTION -> CoroutineScope(Dispatchers.IO).launch { changeTheme(context) }
        }
    }

    private suspend fun fetchSchedule(context: Context) {

        val glanceIds = GlanceAppWidgetManager(context)
            .getGlanceIds(ScheduleWidget::class.java)

        widgetViewModel.onAction(WidgetAction.UpdateWidgetSchedule)

        glanceIds.forEach { id ->

            updateAppWidgetState(context, id) { state ->
                state[WidgetSchedule] = preferencesRepository.getWidgetSchedule() ?: ""
                state[UpdateTime] = preferencesRepository.getLastUpdateWidgetTime() ?: ""
                state[GroupNumber] = preferencesRepository.getUserGroup()
                state[IsLoading] = false
            }

            glanceAppWidget.update(context, id)
        }
    }

    private suspend fun changeWidgetState(context: Context) {

        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(ScheduleWidget::class.java)

        glanceIds.forEach { id ->

            updateAppWidgetState(context, id) { state ->
                state[IsLoading] = true
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