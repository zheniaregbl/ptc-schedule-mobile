package com.syndicate.ptkscheduleapp.feature.schedule

import dev.jordond.connectivity.Connectivity

internal actual fun platformConnectivity(): Connectivity {
    return Connectivity { this.autoStart = true }
}