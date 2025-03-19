package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.SelectedBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.nowDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@Composable
internal fun DayItem(
    selected: Boolean,
    value: LocalDate,
    onChangeDate: (LocalDate) -> Unit
) {

    val currentLocalDate = Clock.System.nowDate()
    val currentThemeMode = MaterialTheme.colorPalette.themeMode

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onChangeDate(value) }
            .border(
                width = 1.5.dp,
                color = when {
                    selected && value == currentLocalDate && currentThemeMode == ThemeMode.LIGHT -> SelectedBlue
                    selected && value == currentLocalDate -> LightBlue
                    selected -> GrayText
                    else -> Color.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        AutoResizeText(
            text = value.dayOfMonth.toString(),
            style = LocalTextStyle.current,
            lineHeight = 15.sp,
            fontWeight = FontWeight.Medium,
            color = when {
                value == currentLocalDate && currentThemeMode == ThemeMode.LIGHT -> SelectedBlue
                value == currentLocalDate -> LightBlue
                else -> MaterialTheme.colorPalette.contentColor
            },
            maxLines = 1,
            fontSizeRange = FontSizeRange(
                min = 12.sp,
                max = 15.sp
            )
        )
    }
}