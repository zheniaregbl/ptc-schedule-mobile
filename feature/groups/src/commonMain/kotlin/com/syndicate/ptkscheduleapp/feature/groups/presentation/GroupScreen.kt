package com.syndicate.ptkscheduleapp.feature.groups.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class GroupScreen : Screen {

    @Composable
    override fun Content() {
        GroupScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }
}

@Composable
internal fun GroupScreenContent(
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {

    }
}