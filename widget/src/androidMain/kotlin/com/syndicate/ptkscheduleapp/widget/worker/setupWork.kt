package com.syndicate.ptkscheduleapp.widget.worker

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.time.Duration
import java.util.concurrent.TimeUnit

fun setUpWorker(workManager: WorkManager) {

    val workRequest = PeriodicWorkRequest.Builder(
        ScheduleWorker::class.java,
        15,
        TimeUnit.MINUTES
    ).setBackoffCriteria(
        BackoffPolicy.LINEAR,
        Duration.ofSeconds(10)
    ).build()

    workManager.enqueueUniquePeriodicWork(
        "scheduleWork",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}