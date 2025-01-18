package com.syndicate.ptkscheduleapp.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    data object GroupScreen : SharedScreen()
}