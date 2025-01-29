package com.syndicate.ptkscheduleapp.feature.schedule.presentation.util.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal fun Modifier.coloredShadow(
    shape: Shape,
    color: Color,
    blurRadius: Dp = 4.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = then(
    drawBehind {

        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        if (blurRadius.toPx() > 0f) { frameworkPaint.setMaskFilter(blurRadius.toPx()) }

        frameworkPaint.setColor(color.toArgb())

        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.translate(offsetX.toPx(), offsetY.toPx())
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
        }
    }
)