package com.syndicate.ptkscheduleapp

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.feature.groups.di.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.di.featureScheduleScreenModule

fun MainViewController() = ComposeUIViewController(
    configure = {

        ScreenRegistry {
            featureGroupsScreenModule()
            featureScheduleScreenModule()
        }

        initKoin()
    }
) { App() }