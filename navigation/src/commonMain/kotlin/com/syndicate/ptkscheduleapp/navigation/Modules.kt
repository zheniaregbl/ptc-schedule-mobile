package com.syndicate.ptkscheduleapp.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.syndicate.ptkscheduleapp.feature.select_course.presentation.SelectCourseScreen

val featureSearchModule = screenModule {
    register<SharedScreen.SelectCourse> { SelectCourseScreen() }
}