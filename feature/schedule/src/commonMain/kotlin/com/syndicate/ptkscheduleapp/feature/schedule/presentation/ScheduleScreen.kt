package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.viewmodel.koinViewModel

internal class ScheduleScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = koinViewModel<ScheduleViewModel>()

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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("Schedule screen")
    }
}