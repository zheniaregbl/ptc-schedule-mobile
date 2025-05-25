package com.syndicate.ptkscheduleapp.feature.role.presentation.components

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import io.github.zheniaregbl.zephyr.foundation.choice_control.ZephyrRadioButton

@Composable
internal fun RoleItem(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ZephyrRadioButton(
            selected = isSelected,
            isOutline = false,
            size = 30.dp,
            color = Color(0xFF4B71FF),
            animationSpec = tween(
                durationMillis = 200,
                easing = EaseOut
            ),
            onClick = onClick
        )

        Text(
            text = label,
            style = LocalTextStyle.current,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            color = MaterialTheme.colorPalette.contentColor
        )
    }
}