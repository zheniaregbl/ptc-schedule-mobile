package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayText
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.HslColor
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.resources.Res
import com.syndicate.ptkscheduleapp.feature.schedule.resources.replacement_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun PairCard(
    modifier: Modifier = Modifier,
    pair: PairItem = PairItem(),
    isDark: Boolean = false
) {

    Box(modifier = modifier) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(vertical = 11.dp)
                .padding(start = 20.dp)
        ) {

            ColorLine(
                modifier = Modifier.fillMaxHeight(),
                hsl = pair.color
            )

            Spacer(modifier = Modifier.width(10.dp))

            PairInfo(
                pairItem = pair,
                isLast = true,
                isDark = isDark
            )
        }

        if (pair.isReplacement)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 10.dp,
                        end = 10.dp
                    )
            ) {
                Image(
                    painter = painterResource(Res.drawable.replacement_svg),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(GrayText)
                )
            }
    }
}

@Composable
internal fun PairCard(
    modifier: Modifier = Modifier,
    pairList: List<PairItem> = listOf(
        PairItem(),
        PairItem()
    ),
    isDark: Boolean = false
) {

    Box(modifier = modifier) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(vertical = 11.dp)
                .padding(
                    start = 20.dp,
                    end = 50.dp
                )
        ) {

            ColorLine(
                modifier = Modifier.fillMaxHeight(),
                hsl = pairList.first().color
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                pairList.forEachIndexed { index, lessonItem ->
                    PairInfo(
                        pairItem = lessonItem,
                        isLast = index == pairList.lastIndex,
                        isDark = isDark
                    )
                }
            }
        }

        if (pairList.first().isReplacement || pairList.last().isReplacement)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 10.dp,
                        end = 10.dp
                    )
            ) {
                Image(
                    painter = painterResource(Res.drawable.replacement_svg),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(GrayText)
                )
            }
    }
}

@Composable
internal fun PairInfo(
    pairItem: PairItem = PairItem(),
    isLast: Boolean = false,
    isDark: Boolean = false
) {

    Column(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .padding(end = 50.dp)
    ) {

        Text(
            text = pairItem.time,
            style = LocalTextStyle.current,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color.White else Color.Black
        )

        Text(
            text = if (pairItem.subject == ""
                || pairItem.subject.lowercase() == "не будет") "Не будет" else pairItem.subject,
            style = LocalTextStyle.current,
            lineHeight = 15.sp,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = GrayText
        )

        Spacer(Modifier.height(3.dp))

        if (pairItem.subject != ""
            && pairItem.subject.lowercase() != "не будет") {

            var text = if (pairItem.room.lowercase() != "дистанционно")
                "${pairItem.teacher}, кабинет ${pairItem.room.lowercase()}"
            else
                "${pairItem.teacher}, ${pairItem.room.lowercase()}"

            if (pairItem.place.lowercase() != "птк" && pairItem.room.lowercase() != "дистанционно")
                text += ", ${pairItem.place}"

            Text(
                text = text,
                style = LocalTextStyle.current,
                lineHeight = 15.sp,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color.White else Color.Black
            )
        }

        if (pairItem.subgroupNumber != 0) {

            Spacer(Modifier.height(3.dp))

            Text(
                text = "п/г ${pairItem.subgroupNumber}",
                style = LocalTextStyle.current,
                lineHeight = 15.sp,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color.White else Color.Black
            )
        }

        if (!isLast) {

            Spacer(modifier = Modifier.height(6.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(GrayText)
            )
        }
    }
}

@Composable
private fun ColorLine(
    modifier: Modifier = Modifier,
    hsl: HslColor,
) {

    Spacer(
        modifier = modifier
            .width(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Color.hsl(
                    hue = hsl.hue,
                    saturation = if (hsl.saturation > 0.75f) 0.75f else hsl.saturation,
                    lightness = if (hsl.lightness < 0.6f) 0.6f else hsl.lightness
                )
            )
    )
}