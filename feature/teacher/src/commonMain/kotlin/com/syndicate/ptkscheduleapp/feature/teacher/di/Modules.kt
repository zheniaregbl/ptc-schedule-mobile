package com.syndicate.ptkscheduleapp.feature.teacher.di

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.teacher.presentation.TeacherScreen

val featureTeacherScreenModule = screenModule {
    register<SharedScreen.TeacherScreen> { TeacherScreen() }
}