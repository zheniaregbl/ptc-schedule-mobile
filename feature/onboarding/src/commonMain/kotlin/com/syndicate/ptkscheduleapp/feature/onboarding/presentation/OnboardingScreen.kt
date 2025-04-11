package com.syndicate.ptkscheduleapp.feature.onboarding.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.syndicate.ptkscheduleapp.core.common.util.PlatformName
import com.syndicate.ptkscheduleapp.core.common.util.platformName
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.components.OnboardingPage
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.components.OnboardingPageData
import com.syndicate.ptkscheduleapp.feature.onboarding.presentation.components.PageIndicator
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.Res
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.page1
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.page2
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.page3
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.page4
import com.syndicate.ptkscheduleapp.feature.onboarding.resources.page5
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.AnimatedButton
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.ZephyrButtonColor
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

internal class OnboardingScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val roleScreen = rememberScreen(SharedScreen.RoleScreen)

        val viewModel = koinViewModel<OnboardingViewModel>()
        val isBackgroundPermissionGranted by viewModel.isBackgroundPermissionGranted.collectAsState()

        OnboardingScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEFDF8))
                .systemBarsPadding(),
            isBackgroundPermissionGranted = isBackgroundPermissionGranted,
            onAction = { action ->
                when (action) {
                    OnboardingAction.NavigateToRoleSelection ->
                        navigator.replace(roleScreen)
                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}

@Composable
internal fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    isBackgroundPermissionGranted: Boolean? = null,
    onAction: (OnboardingAction) -> Unit
) {

    val scope = rememberCoroutineScope()

    val onboardingPageContent = listOf(
        OnboardingPageData(
            Res.drawable.page1,
            "Устали от расписания на сайте и разных таблицах? Теряетесь в заменах?"
        ),
        OnboardingPageData(
            Res.drawable.page2,
            "Теперь все в одном приложении и не только для студентов, но даже для преподавателей."
        ),
        OnboardingPageData(
            Res.drawable.page3,
            "Быстрый доступ к расписанию даже когда вы “не в сети”."
        ),
        OnboardingPageData(
            Res.drawable.page4,
            "Начните использовать Расписание ПТК — и больше никогда не пропускайте изменения в расписании!"
        ),
        OnboardingPageData(
            Res.drawable.page5,
            "Отключите оптимизацию батареи для корректной работы приложения в фоновом режиме."
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 5 }
    )

    LaunchedEffect(isBackgroundPermissionGranted) {
        if (pagerState.currentPage == 4 && isBackgroundPermissionGranted != null) {
            if (isBackgroundPermissionGranted) {
                onAction(OnboardingAction.NavigateToRoleSelection)
            } else {
                onAction(OnboardingAction.OnRequestBackgroundPermission)
            }
        }
    }

    Box(modifier = modifier) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->

            OnboardingPage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                data = onboardingPageContent[page]
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 140.dp)
                .height(18.dp),
            visible = pagerState.currentPage < onboardingPageContent.lastIndex,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            PageIndicator(currentPage = pagerState.currentPage)
        }

        AnimatedButton(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 40.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            text = "Далее",
            colors = ZephyrButtonColor().copy(
                inactiveColor = Color(0xFF135B97),
                pressedColor = Color(0xFF459BE2)
            ),
            onClick = {

                if (pagerState.currentPage == 3 && platformName == PlatformName.IOS) {
                    onAction(OnboardingAction.NavigateToRoleSelection)
                    return@AnimatedButton
                }

                if (pagerState.currentPage == 4) {
                    onAction(OnboardingAction.CheckBackgroundPermission)
                    return@AnimatedButton
                }

                if (pagerState.currentPage != onboardingPageContent.lastIndex) {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow
                            )
                        )
                    }
                }
            }
        )
    }
}