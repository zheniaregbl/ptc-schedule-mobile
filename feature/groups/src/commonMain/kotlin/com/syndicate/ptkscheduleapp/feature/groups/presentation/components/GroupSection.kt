package com.syndicate.ptkscheduleapp.feature.groups.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun GroupSection(
    modifier: Modifier = Modifier
) {

    val groupPickerState = rememberPickerState()

    Box(modifier = modifier) {

        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "Выберите группу",
            style = LocalTextStyle.current,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = Color.Black
        )

        GroupPicker(
            modifier = Modifier
                .align(Alignment.Center)
                .width(130.dp),
            textModifier = Modifier.padding(8.dp),
            state = groupPickerState,
            items = listOf("1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998"),
            visibleItemsCount = 5,
            textStyle = LocalTextStyle.current,
            fontSize = 40.sp
        )
    }
}