package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.theme.DarkColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightRed
import com.syndicate.ptkscheduleapp.core.presentation.theme.LocalColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.SandColor
import com.syndicate.ptkscheduleapp.core.presentation.theme.SelectedBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.TelegramLogoColor
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.ScheduleAction
import com.syndicate.ptkscheduleapp.feature.schedule.resources.Res
import com.syndicate.ptkscheduleapp.feature.schedule.resources.group_svg
import com.syndicate.ptkscheduleapp.feature.schedule.resources.telegram_svg
import com.syndicate.ptkscheduleapp.feature.schedule.resources.theme_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun OptionSheetContent(onAction: (ScheduleAction) -> Unit = { }) {

    val colorBorder = MaterialTheme.colorPalette.contentColor.copy(alpha = 0.3f)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        color = MaterialTheme.colorPalette.backgroundColor
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = colorBorder,
                        start = Offset(x = 25.dp.toPx(), y = 0.dp.toPx()),
                        end = Offset(x = size.width - 25.dp.toPx(), y = 0.dp.toPx()),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawLine(
                        color = colorBorder,
                        start = Offset(x = 0.dp.toPx(), y = 25.dp.toPx()),
                        end = Offset(x = 0.dp.toPx(), y = size.height),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawLine(
                        color = colorBorder,
                        start = Offset(x = size.width, y = 25.dp.toPx()),
                        end = Offset(x = size.width, y = size.height),
                        strokeWidth = 4.dp.toPx()
                    )

                    drawArc(
                        color = colorBorder,
                        startAngle = -90f,
                        sweepAngle = -90f,
                        useCenter = false,
                        topLeft = Offset(x = 0f, y = 0.dp.toPx()),
                        size = Size(25.dp.toPx() * 2, 25.dp.toPx() * 2),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    drawArc(
                        color = colorBorder,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(
                            x = size.width - 25.dp.toPx() * 2,
                            y = 0.dp.toPx()
                        ),
                        size = Size(25.dp.toPx() * 2, 25.dp.toPx() * 2),
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(
                        width = 60.dp,
                        height = 3.dp
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = colorBorder)
            )

            Spacer(modifier = Modifier.height(44.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorPalette.thirdlyColor)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                ChangeGroupSection(onClick = { onAction(ScheduleAction.NavigateToGroupSelection) })

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(1.dp)
                        .background(MaterialTheme.colorPalette.otherColor.copy(0.2f))
                )

                ChangeThemeSection(onClickItem = {  })
            }

            TelegramButton(onClick = { })

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ChangeGroupSection(onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(Res.drawable.group_svg),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorPalette.otherColor)
        )

        Text(
            text = "Изменить группу",
            style = LocalTextStyle.current,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorPalette.otherColor
        )
    }
}

@Composable
private fun ChangeThemeSection(onClickItem: (ThemeMode) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(Res.drawable.theme_svg),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorPalette.otherColor)
            )

            Text(
                text = "Изменить тему",
                style = LocalTextStyle.current,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorPalette.otherColor
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            ThemeMode.entries.forEach { theme ->

                ThemeBox(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .size(68.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    themeMode = theme,
                    onClick = { onClickItem(theme) }
                )
            }
        }
    }
}

@Composable
private fun ThemeBox(
    modifier: Modifier,
    themeMode: ThemeMode,
    onClick: () -> Unit
) {

    val currentThemeMode = LocalColorPalette.current.themeMode
    val boxPalette = when (themeMode) {
        ThemeMode.LIGHT -> LightColorPalette
        ThemeMode.GRAY -> GrayColorPalette
        ThemeMode.DARK -> DarkColorPalette
    }

    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(boxPalette.backgroundColor)
            .border(
                width = 2.5.dp,
                shape = RoundedCornerShape(8.dp),
                color = when {
                    currentThemeMode == themeMode && themeMode == ThemeMode.LIGHT -> SelectedBlue
                    currentThemeMode == themeMode -> LightBlue
                    else -> boxPalette.secondaryColor
                }
            )
    ) {

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight()
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(100))
                .width(5.dp)
                .background(
                    color = when (themeMode) {
                        ThemeMode.LIGHT -> LightRed
                        ThemeMode.GRAY -> SandColor
                        ThemeMode.DARK -> LightBlue
                    }
                )
        )
    }
}

@Composable
private fun TelegramButton(onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = TelegramLogoColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 20.dp,
                vertical = 14.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(Res.drawable.telegram_svg),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Наш Telegram-канал",
                style = LocalTextStyle.current,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}