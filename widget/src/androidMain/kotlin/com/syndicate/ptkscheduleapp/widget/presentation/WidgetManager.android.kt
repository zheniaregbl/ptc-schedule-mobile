package com.syndicate.ptkscheduleapp.widget.presentation

import android.content.Context
import android.content.Intent
import com.syndicate.ptkscheduleapp.widget.ScheduleWidgetReceiver

actual class WidgetManager(private val context: Context) {

    actual fun updateWidget() {
        val intent = Intent(context, ScheduleWidgetReceiver::class.java).apply {
            action = ScheduleWidgetReceiver.UPDATE_ACTION
        }
        context.sendBroadcast(intent)
    }
}