package com.syndicate.ptkscheduleapp.feature.schedule

import androidx.compose.runtime.Composable
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.compose.ConnectivityState

internal expect fun platformConnectivity(): Connectivity

@Composable
internal expect fun createConnectivityState(autoStart: Boolean = true): ConnectivityState