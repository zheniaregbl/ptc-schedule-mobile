package com.syndicate.ptkscheduleapp.widget.presentation.components

import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import com.syndicate.ptkscheduleapp.widget.presentation.util.extension.textAsBitmap

@Composable
fun GlanceText(
    modifier: GlanceModifier = GlanceModifier,
    text: String,
    maxWidth: Dp = 0.dp,
    fontSize: TextUnit = 15.sp,
    @FontRes font: Int,
    color: Color = Color.Black
) {

    Image(
        modifier = modifier,
        provider = ImageProvider(
            LocalContext.current.textAsBitmap(
                text = text,
                maxWidth = maxWidth,
                font = font,
                fontSize = fontSize,
                color = color
            )
        ),
        contentDescription = null
    )
}