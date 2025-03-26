package com.syndicate.ptkscheduleapp.widget

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

class ScheduleWidgetUpdateAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val intent = Intent(context, ScheduleWidgetReceiver::class.java).apply {
            action = ScheduleWidgetReceiver.UPDATE_ACTION
        }

        context.sendBroadcast(intent)
    }
}