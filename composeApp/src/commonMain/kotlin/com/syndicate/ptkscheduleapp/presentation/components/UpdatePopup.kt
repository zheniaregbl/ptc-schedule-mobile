package com.syndicate.ptkscheduleapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.common.util.BuildConfigProvider
import com.syndicate.ptkscheduleapp.core.presentation.components.DialogPopup
import com.syndicate.ptkscheduleapp.core.presentation.theme.FirstThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.SelectedBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThirdThemeBackground
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.core.update.UpdateInfo
import io.github.zheniaregbl.zephyr.foundation.button.ZephyrButton
import io.github.zheniaregbl.zephyr.foundation.button.ZephyrButtonColor

@Composable
internal fun UpdatePopup(
    showDialog: Boolean,
    updateInfo: UpdateInfo,
    onDismissRequest: () -> Unit
) {

    val themeMode = MaterialTheme.colorPalette.themeMode
    val uriHandler = LocalUriHandler.current

    DialogPopup(
        showDialog = showDialog,
        onDismissRequest = onDismissRequest
    ) {

        UpdatePopupContent(
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
            updateInfo = updateInfo,
            onRedirect = {
                uriHandler.openUri(BuildConfigProvider.RUSTORE_APP_URL)
                onDismissRequest()
            },
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
internal fun UpdatePopupContent(
    modifier: Modifier = Modifier,
    updateInfo: UpdateInfo,
    onRedirect: () -> Unit,
    onDismissRequest: () -> Unit
) {

    val themeMode = MaterialTheme.colorPalette.themeMode

    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Доступно обновление",
                style = LocalTextStyle.current,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorPalette.contentColor
            )

            Text(
                text = "Доступна новая версия приложения. Перейдите в Rustore чтобы обновить приложение до версии ${updateInfo.versionName}.",
                style = LocalTextStyle.current,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorPalette.contentColor
            )

            Row(modifier = Modifier.fillMaxWidth()) {

                ZephyrButton(
                    modifier = Modifier.weight(1f),
                    text = "Позже",
                    colors = ZephyrButtonColor().copy(
                        inactiveColor = Color(0xFF818181).copy(0.3f),
                        pressedColor = Color(0xFF818181).copy(0.2f),
                        textColor = Color(0xFF818181)
                    ),
                    softness = 1f,
                    onClick = onDismissRequest
                )

                Spacer(modifier = Modifier.width(12.dp))

                ZephyrButton(
                    modifier = Modifier.weight(1f),
                    text = "Перейти",
                    colors = ZephyrButtonColor().copy(
                        inactiveColor = if (themeMode == ThemeMode.LIGHT) SelectedBlue.copy(0.4f)
                        else LightBlue.copy(0.4f),
                        pressedColor = if (themeMode == ThemeMode.LIGHT) SelectedBlue.copy(0.25f)
                        else LightBlue.copy(0.25f),
                        textColor = if (themeMode == ThemeMode.LIGHT) SelectedBlue
                        else LightBlue
                    ),
                    softness = 1f,
                    onClick = onRedirect
                )
            }
        }
    }
}