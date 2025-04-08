package com.syndicate.ptkscheduleapp.feature.role.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.syndicate.ptkscheduleapp.core.navigation.SharedScreen
import com.syndicate.ptkscheduleapp.core.presentation.theme.colorPalette
import com.syndicate.ptkscheduleapp.feature.role.presentation.components.RoleItem
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.AnimatedButton
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.ZephyrButtonColor

internal class RoleScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val teacherScreen = rememberScreen(SharedScreen.TeacherScreen)
        val groupScreen = rememberScreen(SharedScreen.GroupScreen)

        RoleScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            onAction = { action ->
                when (action) {
                    RoleAction.NavigateToGroupSelection -> navigator.push(groupScreen)
                    RoleAction.NavigateToTeacherSelection -> navigator.push(teacherScreen)
                }
            }
        )
    }
}

@Composable
internal fun RoleScreenContent(
    modifier: Modifier = Modifier,
    onAction: (RoleAction) -> Unit
) {

    val roleList = listOf("Студент", "Преподаватель")

    var selectedRoleIndex by remember { mutableStateOf(0) }

    Box(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp)
        ) {

            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "Выберите роль",
                style = LocalTextStyle.current,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = MaterialTheme.colorPalette.contentColor
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                roleList.forEachIndexed { index, label ->
                    RoleItem(
                        modifier = Modifier
                            .height(30.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { selectedRoleIndex = index },
                        label = label,
                        isSelected = index == selectedRoleIndex,
                        onClick = { selectedRoleIndex = index }
                    )
                }
            }
        }

        AnimatedButton(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 40.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            text = "Далее",
            colors = ZephyrButtonColor().copy(
                inactiveColor = Color(0xFF4B71FF),
                pressedColor = Color(0xFF95ACFF)
            ),
            onClick = {
                if (selectedRoleIndex == 0) onAction(RoleAction.NavigateToGroupSelection)
                else onAction(RoleAction.NavigateToTeacherSelection)
            }
        )
    }
}