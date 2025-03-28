package com.syndicate.ptkscheduleapp.widget.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.unit.ColorProvider
import com.syndicate.ptkscheduleapp.core.common.util.randomHsl
import com.syndicate.ptkscheduleapp.core.domain.model.HslColor
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayText
import com.syndicate.ptkscheduleapp.widget.R
import com.syndicate.ptkscheduleapp.widget.presentation.util.extension.scaledSp

@Composable
internal fun GlancePairCard(
    modifier: GlanceModifier = GlanceModifier,
    pairItem: PairItem = PairItem(),
    isDarkText: Boolean = true
) {

    Box(
        modifier = modifier
    ) {

        Row(
            modifier = GlanceModifier
                .padding(vertical = 11.dp)
                .padding(start = 20.dp)
        ) {

            GlanceColorLine(
                modifier = GlanceModifier
                    .fillMaxHeight(),
                hsl = pairItem.color
            )

            Spacer(modifier = GlanceModifier.width(10.dp))

            GlancePairInfo(
                pairItem = pairItem,
                isDarkText = isDarkText,
                isLast = true
            )
        }
    }
}

@Composable
internal fun GlancePairCard(
    modifier: GlanceModifier = GlanceModifier,
    pairList: List<PairItem> = listOf(
        PairItem(),
        PairItem()
    ),
    isDarkText: Boolean = true
) {

    Box(modifier = modifier) {

        Row(
            modifier = GlanceModifier
                .padding(vertical = 11.dp)
                .padding(start = 20.dp)
                .padding(end = 50.dp)
        ) {

            GlanceColorLine(
                modifier = GlanceModifier.fillMaxHeight(),
                hsl = pairList.first().color
            )

            Spacer(modifier = GlanceModifier.width(10.dp))

            Column {
                pairList.forEachIndexed { index, pairItem ->
                    GlancePairInfo(
                        pairItem = pairItem,
                        isDarkText = isDarkText,
                        isLast = index == pairList.lastIndex
                    )
                }
            }
        }
    }
}

@Composable
private fun GlancePairInfo(
    pairItem: PairItem = PairItem(),
    isDarkText: Boolean = true,
    isLast: Boolean = false
) {

    val widgetWidth = LocalSize.current.width

    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .padding(end = 50.dp)
    ) {

        GlanceText(
            text = pairItem.time,
            font = R.font.montserrat_bold,
            fontSize = 15.scaledSp(),
            color = if (isDarkText) Color.Black else Color.White
        )

        Spacer(modifier = GlanceModifier.height(3.dp))

        GlanceText(
            text = if (pairItem.subject == ""
                || pairItem.subject.lowercase() == "не будет") "Не будет" else pairItem.subject,
            maxWidth = widgetWidth - 98.dp,
            font = R.font.montserrat_medium,
            fontSize = 12.scaledSp(),
            color = GrayText
        )

        Spacer(modifier = GlanceModifier.height(3.dp))

        if (pairItem.subject != ""
            && pairItem.subject.lowercase() != "не будет") {

            var text = if (pairItem.room.lowercase() != "дистанционно")
                "${pairItem.teacher}, кабинет ${pairItem.room.lowercase()}"
            else
                "${pairItem.teacher}, ${pairItem.room.lowercase()}"

            if (pairItem.place.lowercase() != "птк" && pairItem.room.lowercase() != "дистанционно")
                text += ", ${pairItem.place}"

            GlanceText(
                text = text,
                maxWidth = widgetWidth - 98.dp,
                font = R.font.montserrat_medium,
                fontSize = 12.scaledSp(),
                color = if (isDarkText) Color.Black else Color.White
            )
        }

        if (pairItem.subgroupNumber != 0) {

            GlanceText(
                text = "п/г ${pairItem.subgroupNumber}",
                font = R.font.montserrat_medium,
                fontSize = 12.scaledSp(),
                color = if (isDarkText) Color.Black else Color.White
            )
        }

        if (!isLast) {

            Spacer(modifier = GlanceModifier.height(6.dp))

            Spacer(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(GrayText)
            )
        }
    }
}

@Composable
private fun GlanceColorLine(
    modifier: GlanceModifier = GlanceModifier,
    hsl: HslColor = randomHsl()
) {

    Box(
        modifier = modifier
            .background(
                imageProvider = ImageProvider(R.drawable.color_line_background),
                colorFilter = ColorFilter.tint(
                    ColorProvider(
                        Color.hsl(
                            hue = hsl.hue,
                            saturation = if (hsl.saturation > 0.75f) 0.75f else hsl.saturation,
                            lightness = if (hsl.lightness < 0.6f) 0.6f else hsl.lightness
                        )
                    )
                )
            )
            .width(5.dp)
    ) { }
}