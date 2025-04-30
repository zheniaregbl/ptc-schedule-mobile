package com.syndicate.ptkscheduleapp.presentation.util.extension

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

internal tailrec fun Context.findActivity(): ComponentActivity? =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }