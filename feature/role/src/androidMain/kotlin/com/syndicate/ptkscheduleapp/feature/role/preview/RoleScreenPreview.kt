package com.syndicate.ptkscheduleapp.feature.role.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.role.presentation.RoleScreenContent

@Preview
@Composable
private fun RoleScreenPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorPalette.backgroundColor)
        ) {
            RoleScreenContent(
                modifier = Modifier.fillMaxSize(),
                onAction = {}
            )
        }
    }
}