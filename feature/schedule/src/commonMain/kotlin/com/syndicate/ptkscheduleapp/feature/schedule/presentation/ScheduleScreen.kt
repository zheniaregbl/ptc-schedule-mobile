package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

internal class ScheduleScreen : Screen {

    @Composable
    override fun Content() {
        ScheduleScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }
}

@Composable
internal fun ScheduleScreenContent(
    modifier: Modifier = Modifier
) {


}