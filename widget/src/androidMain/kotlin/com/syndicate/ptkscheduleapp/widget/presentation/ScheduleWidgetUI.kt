package com.syndicate.ptkscheduleapp.widget.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.syndicate.ptkscheduleapp.widget.R

@Composable
fun ScheduleWidgetUI() {

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(
                imageProvider = ImageProvider(R.drawable.widget_background),
                colorFilter = ColorFilter.tint(ColorProvider(Color.White))
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("ScheduleWidget")
    }
}