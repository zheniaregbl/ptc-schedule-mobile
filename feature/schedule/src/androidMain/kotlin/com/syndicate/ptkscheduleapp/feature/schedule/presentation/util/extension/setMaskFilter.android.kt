package com.syndicate.ptkscheduleapp.feature.schedule.presentation.util.extension

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.NativePaint

internal actual fun NativePaint.setMaskFilter(blurRadius: Float) {
    this.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
}