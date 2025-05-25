package com.syndicate.ptkscheduleapp.feature.teacher.presentation.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import io.github.zheniaregbl.zephyr.foundation.input.text_field.ZephyrTextField
import io.github.zheniaregbl.zephyr.foundation.input.text_field.ZephyrTextFieldColor
import io.github.zheniaregbl.zephyr.foundation.input.text_field.ZephyrTextSelectionColors

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {

    val currentThemeMode = MaterialTheme.colorPalette.themeMode

    ZephyrTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = "Фамилия И.О.",
        textStyle = LocalTextStyle.current.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = MaterialTheme.colorPalette.contentColor
        ),
        colors = ZephyrTextFieldColor(
            hoveredTextColor = MaterialTheme.colorPalette.contentColor,
            focusedTextColor = MaterialTheme.colorPalette.contentColor,
            unfocusedTextColor = MaterialTheme.colorPalette.contentColor,
            hoveredBorderColor = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.8f)
            else Color(0xFF4B71FF).copy(0.8f),
            focusedBorderColor = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.8f)
            else Color(0xFF4B71FF).copy(0.8f),
            unfocusedBorderColor = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.8f)
            else Color(0xFF4B71FF).copy(0.8f),
            hoveredPlaceholderColor = MaterialTheme.colorPalette.contentColor.copy(0.5f),
            focusedPlaceholderColor = MaterialTheme.colorPalette.contentColor.copy(0.5f),
            unfocusedPlaceholderColor = MaterialTheme.colorPalette.contentColor.copy(0.5f),
            cursorColor = if (currentThemeMode == ThemeMode.DARK) LightBlue
            else Color(0xFF4B71FF),
            textSelectionColors = ZephyrTextSelectionColors(
                handleColor = if (currentThemeMode == ThemeMode.DARK) LightBlue
                else Color(0xFF4B71FF),
                backgroundColor = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.4f)
                else Color(0xFF4B71FF).copy(0.4f)
            )
        )
    )
}