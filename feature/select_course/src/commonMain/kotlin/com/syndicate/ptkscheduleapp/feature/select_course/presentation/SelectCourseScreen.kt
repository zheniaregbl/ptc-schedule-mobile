package com.syndicate.ptkscheduleapp.feature.select_course.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.syndicate.ptkscheduleapp.feature.select_course.presentation.components.CourseSection
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.AnimatedButton
import com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button.ZephyrButtonColor

class SelectCourseScreen : Screen {

    @Composable
    override fun Content() {
        SelectCourseScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }
}

@Composable
internal fun SelectCourseScreenContent(
    modifier: Modifier = Modifier
) {

    var selectedCourse by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp),
            text = "Выберите курс",
            style = LocalTextStyle.current,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            color = Color.Black
        )

        CourseSection(
            modifier = Modifier.align(Alignment.Center),
            courseProvider = { selectedCourse },
            onCourseClick = { selectedCourse = it }
        )

        AnimatedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            text = "Далее",
            colors = ZephyrButtonColor().copy(
                inactiveColor = Color(0xFF4B71FF),
                pressedColor = Color(0xFF95ACFF)
            ),
            onClick = { }
        )
    }
}