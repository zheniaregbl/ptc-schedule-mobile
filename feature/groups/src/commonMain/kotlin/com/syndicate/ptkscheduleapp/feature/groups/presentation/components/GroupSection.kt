package com.syndicate.ptkscheduleapp.feature.groups.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.components.CircleLoading
import com.syndicate.ptkscheduleapp.core.presentation.theme.LocalColorPalette
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.groups.presentation.DisplayResult
import com.syndicate.ptkscheduleapp.feature.groups.presentation.GroupState

@Composable
internal fun GroupSection(
    modifier: Modifier = Modifier,
    state: GroupState,
    groupPickerState: PickerState
) {

    val currentThemeMode = LocalColorPalette.current.themeMode

    Box(modifier = modifier) {

        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "Выберите группу",
            style = LocalTextStyle.current,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = MaterialTheme.colorPalette.contentColor
        )

        state.toUiState().DisplayResult(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            onIdle = {},
            onLoading = {
                CircleLoading(
                    size = 60.dp,
                    color = if (currentThemeMode == ThemeMode.LIGHT) Color(0xFF4B71FF)
                    else Color.White
                )
            },
            onError = {},
            onSuccess = { screenState ->
                GroupPicker(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(130.dp),
                    textModifier = Modifier.padding(8.dp),
                    state = groupPickerState,
                    items = screenState.groupList,
                    visibleItemsCount = 5,
                    textStyle = LocalTextStyle.current,
                    fontSize = 40.sp
                )
            }
        )
    }
}