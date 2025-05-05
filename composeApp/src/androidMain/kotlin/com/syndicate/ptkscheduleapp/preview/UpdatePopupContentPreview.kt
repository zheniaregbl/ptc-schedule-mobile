package com.syndicate.ptkscheduleapp.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.core.update.UpdateInfo
import com.syndicate.ptkscheduleapp.presentation.components.UpdatePopup

@Preview
@Composable
private fun UpdatePopupContentPreview() {
    AppTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorPalette.backgroundColor)
        ) {
            UpdatePopup(
                showDialog = true,
                updateInfo = UpdateInfo(
                    versionName = "2.0.0",
                    info = "Рады сообщить, что в последнем обновлении появилось новое диалоговое окно, которое будет сообщать о всех свежих обновлениях и улучшениях. Теперь вам не придется искать информацию о новых функциях или исправлениях багов — после каждого обновления вы автоматически увидите окно с подробностями.\nТакже в обновлении приложения были внесены следующие изменения:\n- Исправлены ошибки в отображении текста на виджете. - Исправлена ошибка в сдвиге расписания на виджете. - Исправлены зависания календаря."
                ),
                onDismissRequest = { }
            )
        }
    }
}