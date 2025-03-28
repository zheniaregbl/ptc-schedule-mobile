package com.syndicate.ptkscheduleapp.widget

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

internal class ChangeWidgetThemeAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val intent = Intent(context, ScheduleWidgetReceiver::class.java).apply {
            action = ScheduleWidgetReceiver.CHANGE_THEME_ACTION
        }

        context.sendBroadcast(intent)
    }
}