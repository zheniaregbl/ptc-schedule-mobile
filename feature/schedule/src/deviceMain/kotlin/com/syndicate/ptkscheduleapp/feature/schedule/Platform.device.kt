package com.syndicate.ptkscheduleapp.feature.schedule

import androidx.compose.runtime.Composable
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.compose.ConnectivityState
import dev.jordond.connectivity.compose.rememberConnectivityState

internal actual fun platformConnectivity(): Connectivity {
    return Connectivity { this.autoStart = true }
}

@Composable
internal actual fun createConnectivityState(autoStart: Boolean): ConnectivityState {
    return rememberConnectivityState {
        this.autoStart = autoStart
    }
}