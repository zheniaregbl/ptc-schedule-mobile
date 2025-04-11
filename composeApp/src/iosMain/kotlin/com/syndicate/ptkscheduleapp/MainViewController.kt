package com.syndicate.ptkscheduleapp

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.syndicate.ptkscheduleapp.di.initKoin
import com.syndicate.ptkscheduleapp.di.iosPlatformModules
import com.syndicate.ptkscheduleapp.feature.groups.di.featureGroupsScreenModule
import com.syndicate.ptkscheduleapp.feature.onboarding.di.featureOnboardingScreenModule
import com.syndicate.ptkscheduleapp.feature.role.di.featureRoleScreenModule
import com.syndicate.ptkscheduleapp.feature.schedule.di.featureScheduleScreenModule
import com.syndicate.ptkscheduleapp.feature.teacher.di.featureTeacherScreenModule

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