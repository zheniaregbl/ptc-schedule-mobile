package com.syndicate.ptkscheduleapp.feature.onboarding.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0..3) {
            AnimatedDot(isSelected = currentPage == index)
        }
    }
}

@Composable
private fun AnimatedDot(
    isSelected: Boolean
) {

    val size by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 10.dp
    )
    val color by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF135B97) else Color(0xFFDBEAEE)
    )

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
            .background(color)
    )
}