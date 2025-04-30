package com.syndicate.ptkscheduleapp.feature.schedule.common.util

import io.github.oshai.kotlinlogging.KotlinLogging

internal object Logger {

    private val logger = KotlinLogging.logger("Schedule module")

    fun info(message: String) = logger.info { message }
    fun warn(message: String) = logger.warn { message }
    fun error(message: String) = logger.error { message }
}