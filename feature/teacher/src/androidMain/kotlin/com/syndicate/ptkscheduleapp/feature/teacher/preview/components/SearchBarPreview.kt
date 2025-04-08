package com.syndicate.ptkscheduleapp.feature.teacher.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.ThemeMode
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.components.SearchBar

@Preview
@Composable
private fun SearchBarPreview() {

    AppTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorPalette.backgroundColor)
                .padding(10.dp)
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                onImeSearch = {}
            )
        }
    }
}