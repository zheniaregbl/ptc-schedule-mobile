package com.syndicate.ptkscheduleapp.feature.onboarding.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun OnboardingPage(
    modifier: Modifier = Modifier,
    data: OnboardingPageData
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(0.8f),
            painter = painterResource(data.image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = data.text,
            style = LocalTextStyle.current,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = MaterialTheme.colorPalette.contentColor
        )
    }
}