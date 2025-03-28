package com.syndicate.ptkscheduleapp.feature.schedule.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import com.syndicate.ptkscheduleapp.core.presentation.components.DialogPopup
import com.syndicate.ptkscheduleapp.core.presentation.theme.FirstThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayText
import com.syndicate.ptkscheduleapp.core.presentation.theme.SecondThemeBackground
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.presentation.theme.GrayThirdTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThirdThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.schedule.resources.Res
import com.syndicate.ptkscheduleapp.feature.schedule.resources.arrow_down_new_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ReplacementPopup(
    showDialog: Boolean,
    pair: List<PairItem>,
    replacement: List<PairItem>,
    newPair: Boolean,
    onDismissRequest: () -> Unit
) {

    val themeMode = MaterialTheme.colorPalette.themeMode

    DialogPopup(
        showDialog = showDialog,
        onDismissRequest = onDismissRequest
    ) {

        ReplacementPopupContent(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    when (themeMode) {
                        ThemeMode.LIGHT -> FirstThemeBackground
                        ThemeMode.GRAY -> ThirdThemeBackground
                        ThemeMode.DARK -> Color(0xFF151515)
                    }
                ),
            pair = pair,
            replacement = replacement,
            newPair = newPair
        )
    }
}

@Composable
internal fun ReplacementPopupContent(
    modifier: Modifier = Modifier,
    pair: List<PairItem>,
    replacement: List<PairItem>,
    newPair: Boolean,
) {

    val themeMode = MaterialTheme.colorPalette.themeMode

    Box(modifier = modifier) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (themeMode == ThemeMode.LIGHT) SecondThemeBackground
                        else GrayThirdTheme
                    )
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = if (newPair) "Пара добавлена!" else "Пара изменена!",
                    style = LocalTextStyle.current,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    color = GrayText
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!newPair) {
                    item {
                        if (pair.size > 1) {

                            PairCard(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                pairList = pair,
                                enabled = false,
                                onClick = { }
                            )

                        } else {

                            PairCard(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                pair = pair.first(),
                                enabled = false,
                                onClick = { }
                            )

                        }
                    }

                    item { Spacer(Modifier.height(10.dp)) }

                    item {
                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(Res.drawable.arrow_down_new_svg),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(GrayText)
                        )
                    }

                    item { Spacer(Modifier.height(10.dp)) }
                } else {
                    item { Spacer(Modifier.height(20.dp)) }
                }

                item {
                    if (replacement.size > 1) {

                        PairCard(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .fillMaxWidth(),
                            pairList = listOf(
                                replacement.first().copy(isReplacement = false),
                                replacement.last()
                            ),
                            enabled = false,
                            onClick = { }
                        )

                    } else {

                        PairCard(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .fillMaxWidth(),
                            pair = replacement.first().copy(isReplacement = false),
                            enabled = false,
                            onClick = { }
                        )

                    }
                }
            }
        }
    }
}