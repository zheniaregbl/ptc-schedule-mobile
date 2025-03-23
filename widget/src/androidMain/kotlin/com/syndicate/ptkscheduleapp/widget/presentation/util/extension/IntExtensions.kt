package com.syndicate.ptkscheduleapp.widget.presentation.util.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.LocalContext

@Composable
fun Int.scaledSp(): TextUnit {
    val value: Int = this

    return with(LocalContext.current.resources.configuration) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}