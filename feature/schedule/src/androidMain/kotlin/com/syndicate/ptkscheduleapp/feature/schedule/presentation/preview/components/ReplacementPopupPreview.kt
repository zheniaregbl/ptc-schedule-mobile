package com.syndicate.ptkscheduleapp.feature.schedule.presentation.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syndicate.ptkscheduleapp.core.presentation.theme.AppTheme
import com.syndicate.ptkscheduleapp.core.presentation.theme.FirstThemeBackground
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.presentation.components.ReplacementPopupContent

@Preview
@Composable
private fun ReplacementPopupPreview() {

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .padding(horizontal = 20.dp)
        ) {
            ReplacementPopupContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(FirstThemeBackground),
                pair = listOf(PairItem(), PairItem()),
                replacement = listOf(PairItem(), PairItem()),
                newPair = false
            )
        }
    }
}