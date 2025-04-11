package com.syndicate.ptkscheduleapp

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.di.iosPlatformModules
import com.syndicate.ptkscheduleapp.feature.groups.navigation.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.onboarding.navigation.featureOnboardingScreenModule
import com.syndicate.ptkscheduleapp.feature.role.navigation.featureRoleScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.navigation.featureScheduleScreenModule
import com.syndicate.ptkscheduleapp.feature.teacher.navigation.featureTeacherScreenModule

fun MainViewController() = ComposeUIViewController(
    configure = {

        ScreenRegistry {
            featureOnboardingScreenModule()
            featureRoleScreenModule()
            featureTeacherScreenModule()
            featureGroupsScreenModule()
            featureScheduleScreenModule()
        }

        initKoin(platformModules = iosPlatformModules)
    }
) { App() }