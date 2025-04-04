package com.syndicate.ptkscheduleapp.widget.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.unit.ColorProvider
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightColorPalette
import com.syndicate.ptkscheduleapp.widget.ChangeWidgetThemeAction
import com.syndicate.ptkscheduleapp.widget.R
import com.syndicate.ptkscheduleapp.widget.ScheduleWidgetUpdateAction
import com.syndicate.ptkscheduleapp.widget.presentation.components.GlancePairCard
import com.syndicate.ptkscheduleapp.widget.presentation.components.GlanceText
import com.syndicate.ptkscheduleapp.widget.presentation.util.extension.scaledSp

@Composable
internal fun ScheduleWidgetUI(
    widgetSchedule: List<List<PairItem>> = emptyList(),
    isAlternativeTheme: Boolean = false,
    isLoading: Boolean = false,
    updateTime: String = "",
    groupNumber: String = ""
) {

    Column(modifier = GlanceModifier.fillMaxWidth()) {

        Column(
            modifier = GlanceModifier
                .defaultWeight()
                .background(
                    imageProvider = ImageProvider(R.drawable.widget_background),
                    colorFilter = ColorFilter
                        .tint(
                            colorProvider = ColorProvider(
                                if (isAlternativeTheme) Color.White else Color.Black
                            )
                        )
                )
                .padding(14.dp)
        ) {

            Box(modifier = GlanceModifier.fillMaxWidth()) {

                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Vertical.CenterVertically,
                    horizontalAlignment = Alignment.Start
                ) {

                    GlanceText(
                        text = "Расписание ПТК",
                        font = R.font.montserrat_bold,
                        fontSize = 15.scaledSp(),
                        color = if (isAlternativeTheme) Color.Black else Color.White
                    )
                }

                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Vertical.CenterVertically,
                    horizontalAlignment = Alignment.End
                ) {

                    GlanceText(
                        text = groupNumber,
                        font = R.font.montserrat_medium,
                        fontSize = 14.scaledSp(),
                        color = if (isAlternativeTheme) Color.Black else Color.White
                    )
                }
            }

            Spacer(modifier = GlanceModifier.height(15.dp))

            LazyColumn(modifier = GlanceModifier.defaultWeight()) {

                if (!isLoading) {

                    if (widgetSchedule.isNotEmpty()) {

                        itemsIndexed(widgetSchedule) { index, pair ->

                            Column(modifier = GlanceModifier.fillMaxWidth()) {

                                if (pair.size > 1) {

                                    GlancePairCard(
                                        modifier = GlanceModifier
                                            .fillMaxWidth()
                                            .background(
                                                imageProvider = ImageProvider(R.drawable.widget_pair_card_background),
                                                colorFilter = ColorFilter.tint(
                                                    colorProvider = ColorProvider(
                                                        if (isAlternativeTheme) GrayColorPalette.backgroundColor
                                                        else LightColorPalette.backgroundColor
                                                    )
                                                )
                                            ),
                                        pairList = pair,
                                        isDarkText = !isAlternativeTheme
                                    )

                                } else {

                                    GlancePairCard(
                                        modifier = GlanceModifier
                                            .fillMaxWidth()
                                            .background(
                                                imageProvider = ImageProvider(R.drawable.widget_pair_card_background),
                                                colorFilter = ColorFilter.tint(
                                                    colorProvider = ColorProvider(
                                                        if (isAlternativeTheme) GrayColorPalette.backgroundColor
                                                        else LightColorPalette.backgroundColor
                                                    )
                                                )
                                            ),
                                        pairItem = pair.first(),
                                        isDarkText = !isAlternativeTheme
                                    )

                                }

                                if (index != widgetSchedule.lastIndex)
                                    Spacer(modifier = GlanceModifier.height(10.dp))
                            }
                        }

                    } else {

                        item {
                            Box(
                                modifier = GlanceModifier
                                    .fillMaxWidth()
                                    .padding(vertical = 30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                GlanceText(
                                    text = "Нет занятий",
                                    font = R.font.montserrat_bold,
                                    fontSize = 16.scaledSp(),
                                    color = if (isAlternativeTheme) Color.Black else Color.White
                                )
                            }
                        }

                    }

                } else {

                    item {
                        Box(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 30.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            GlanceText(
                                text = "Загрузка...",
                                font = R.font.montserrat_bold,
                                fontSize = 16.scaledSp(),
                                color = if (isAlternativeTheme) Color.Black else Color.White
                            )
                        }
                    }

                }
            }

            Spacer(modifier = GlanceModifier.height(15.dp))

            Box(modifier = GlanceModifier.fillMaxWidth()) {

                Image(
                    modifier = GlanceModifier
                        .size(25.dp)
                        .clickable(
                            rippleOverride = R.color.transparent,
                            onClick = actionRunCallback<ChangeWidgetThemeAction>()
                        ),
                    provider = ImageProvider(
                        if (isAlternativeTheme) R.drawable.dark_mode_svg
                        else R.drawable.light_mode_svg
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter
                        .tint(
                            colorProvider = ColorProvider(
                                if (isAlternativeTheme) Color.Black else Color.White
                            )
                        )
                )

                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.End
                ) {

                    GlanceText(
                        text = updateTime,
                        font = R.font.montserrat_medium,
                        fontSize = 13.scaledSp(),
                        color = if (isAlternativeTheme) Color.Black else Color.White
                    )

                    Spacer(modifier = GlanceModifier.width(6.dp))

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier = GlanceModifier.size(25.dp),
                            color = ColorProvider(color = LightBlue)
                        )

                    } else {

                        Image(
                            modifier = GlanceModifier
                                .size(25.dp)
                                .clickable(
                                    rippleOverride = R.color.transparent,
                                    onClick = actionRunCallback<ScheduleWidgetUpdateAction>()
                                ),
                            provider = ImageProvider(R.drawable.refresh_svg),
                            contentDescription = null,
                            colorFilter = ColorFilter
                                .tint(
                                    colorProvider = ColorProvider(
                                        if (isAlternativeTheme) Color.Black else Color.White
                                    )
                                )
                        )

                    }
                }
            }
        }
    }
}