package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.components.AutoResizeText
import com.syndicate.ptkscheduleapp.core.presentation.components.FontSizeRange
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayText
import com.syndicate.ptkscheduleapp.core.presentation.theme.SelectedBlue
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun DayItem(
    selected: Boolean,
    value: LocalDate,
    onChangeDate: (LocalDate) -> Unit
) {

    val currentLocalDate = Clock.System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onChangeDate(value) }
            .border(
                width = 1.5.dp,
                color = when {
                    selected && value == currentLocalDate -> SelectedBlue
                    selected -> GrayText
                    else -> Color.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        AutoResizeText(
            text = value.dayOfMonth.toString(),
            fontWeight = FontWeight.Medium,
            color = if (value == currentLocalDate) SelectedBlue
            else Color.Black,
            maxLines = 1,
            fontSizeRange = FontSizeRange(
                min = 12.sp,
                max = 15.sp
            )
        )
        /*Text(
            text = value.dayOfMonth.toString(),
            style = LocalTextStyle.current,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = if (value == currentLocalDate) SelectedBlue
            else Color.Black
        )*/
    }
}