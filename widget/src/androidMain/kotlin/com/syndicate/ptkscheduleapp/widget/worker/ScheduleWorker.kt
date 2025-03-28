package com.syndicate.ptkscheduleapp.widget.worker

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syndicate.ptkscheduleapp.widget.ScheduleWidgetReceiver

internal class ScheduleWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val intent = Intent(context, ScheduleWidgetReceiver::class.java).apply {
            action = ScheduleWidgetReceiver.UPDATE_ACTION
        }
        applicationContext.sendBroadcast(intent)

        return Result.success()
    }
}