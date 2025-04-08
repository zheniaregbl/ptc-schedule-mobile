package com.syndicate.ptkscheduleapp.feature.teacher.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.theme.LightBlue
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.teacher.resources.Res
import com.syndicate.ptkscheduleapp.feature.teacher.resources.search_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onImeSearch: () -> Unit
) {

    val currentThemeMode = MaterialTheme.colorPalette.themeMode

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = if (currentThemeMode == ThemeMode.DARK) LightBlue
            else Color(0xFF4B71FF),
            backgroundColor = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.4f)
            else Color(0xFF4B71FF).copy(0.4f)
        )
    ) {

        BasicTextField(
            modifier = modifier,
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = MaterialTheme.colorPalette.contentColor
            ),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onImeSearch() }),
            singleLine = true,
            cursorBrush = SolidColor(
                if (currentThemeMode == ThemeMode.DARK) LightBlue
                else Color(0xFF4B71FF)
            )
        ) { innerTextField ->

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (currentThemeMode == ThemeMode.DARK) LightBlue.copy(0.8f)
                        else Color(0xFF4B71FF).copy(0.8f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(Res.drawable.search_svg),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = if (value.isNotBlank()) MaterialTheme.colorPalette.contentColor
                        else MaterialTheme.colorPalette.contentColor.copy(0.5f)
                    )
                )

                Box(modifier = Modifier.weight(1f)) {

                    innerTextField()

                    if (value.isBlank()) {
                        Text(
                            text = "Фамилия И.О.",
                            style = LocalTextStyle.current,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorPalette.contentColor.copy(0.5f)
                        )
                    }
                }
            }
        }
    }
}