package com.syndicate.ptkscheduleapp.widget.presentation.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.TypedValueCompat.dpToPx
import androidx.core.util.TypedValueCompat.spToPx

fun Context.textAsBitmap(
    text: String,
    maxWidth: Dp,
    font: Int,
    fontSize: TextUnit,
    color: Color = Color.Black,
    letterSpacing: Float = 0.1f
): Bitmap {

    val maxWidthPx = dpToPx(maxWidth.value, this.resources.displayMetrics)

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = spToPx(fontSize.value, this.resources.displayMetrics)
    paint.color = color.toArgb()
    paint.letterSpacing = letterSpacing
    paint.typeface = ResourcesCompat.getFont(this, font)

    if (maxWidthPx.toInt() != 0) {

        val listString = splitTextIntoLines(
            splitToWords(text),
            maxWidth = maxWidthPx.toInt(),
            font = font,
            fontSize = fontSize,
            color = color,
            letterSpacing = letterSpacing,
            context = this
        )

        val baseline = -paint.ascent()
        val lineHeight = baseline.toInt() + paint.descent().toInt()

        val height = if (listString.size != 1)
            listString.size * lineHeight + (((listString.size - 1) * lineHeight) / 2)
        else baseline.toInt() + paint.descent().toInt()
        val image = Bitmap.createBitmap(maxWidthPx.toInt(), height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)

        listString.forEachIndexed { index, line ->
            canvas.drawText(line, 0f, baseline + baseline * index + (baseline * index) / 2, paint)
        }

        return image

    } else {

        val image: Bitmap

        val baseline = -paint.ascent()
        val width = if (maxWidthPx != 0f) maxWidthPx.toInt() else (paint.measureText(text)).toInt()
        val height = (baseline + paint.descent()).toInt()

        image = if (width > 0 && height > 0) {
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(image)
        canvas.drawText(text, 0f, baseline, paint)

        return image
    }
}

fun splitToWords(input: String): List<String> {
    return input.split("\\s+".toRegex()).filter { it.isNotEmpty() }
}

private tailrec fun splitTextIntoLines(
    words: List<String>,
    result: List<String> = emptyList(),
    maxWidth: Int,
    font: Int,
    fontSize: TextUnit,
    color: Color = Color.Black,
    letterSpacing: Float = 0.1f,
    context: Context
) : List<String> {

    return if (words.isEmpty()) {

        result
    } else {

        val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = spToPx(fontSize.value, context.resources.displayMetrics)
        paint.color = color.toArgb()
        paint.letterSpacing = letterSpacing
        paint.typeface = ResourcesCompat.getFont(context, font)

        var tempWords = words
        var text = ""

        while (paint.measureText(text + "${tempWords.first()} ").toInt() <= maxWidth) {
            text += "${tempWords.first()} "
            tempWords = tempWords.drop(1)
            if (tempWords.isEmpty())
                break
        }

        splitTextIntoLines(
            tempWords,
            result + text,
            maxWidth,
            font,
            fontSize,
            color,
            letterSpacing,
            context
        )
    }
}