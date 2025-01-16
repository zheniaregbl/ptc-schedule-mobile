package com.syndicate.ptkscheduleapp.ui_kit.foundations.element.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import com.syndicate.ptkscheduleapp.ui_kit.foundations.core.theme.DisableColor
import com.syndicate.ptkscheduleapp.ui_kit.foundations.core.theme.PrimaryColor
import com.syndicate.ptkscheduleapp.ui_kit.foundations.core.theme.TertiaryOne
import kotlinx.coroutines.launch

/**
 * The element for displaying the button. The button has an indentation animation when pressed, as well as a color change animation.
 * @param modifier The modifier to be applied to the layout.
 * @param text The text to display on the button.
 * @param enabled The button's activity status.
 * @param isOutline If `true`, the button will be rendered in an outline style (transparent background with a border).
 * @param colors Object for getting button colors in different states. See [ZephyrButtonColor].
 * @param cornerRadius Rounding the edges of the button.
 * @param softness The scale factor when the button is pressed. Must be between `0f` and `1f`.
 * @param onClick The lambda to be executed when the button is clicked.
 * */
@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isOutline: Boolean = false,
    colors: ZephyrButtonColor = ZephyrButtonColor(),
    cornerRadius: Dp = 8.dp,
    softness: Float = 0.98f,
    onClick: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }

    val buttonSoftness by remember { mutableFloatStateOf(softness.fastCoerceIn(0.0f, 1.0f)) }

    val buttonColor by remember {
        derivedStateOf {
            when {
                isOutline && isPressed -> colors.pressedColor.copy(alpha = 0.1f)
                isOutline -> Color.Transparent
                !enabled -> colors.disableColor
                isPressed -> colors.pressedColor
                else -> colors.inactiveColor
            }
        }
    }

    val borderColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disableColor
                isPressed -> colors.pressedColor
                else -> colors.inactiveColor
            }
        }
    }

    val buttonTextColor by remember {
        derivedStateOf {
            when {
                isOutline && !enabled -> colors.disableColor
                isOutline && isPressed -> colors.pressedColor
                isOutline && !isPressed -> colors.inactiveColor
                else -> colors.textColor
            }
        }
    }

    LaunchedEffect(interactionSource) {

        interactionSource.interactions.collect { interaction ->

            when (interaction) {

                is PressInteraction.Press -> {
                    isPressed = true
                    scope.launch { scale.animateTo(buttonSoftness, animationSpec = spring()) }
                }

                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    isPressed = false
                    scope.launch { scale.animateTo(1f, animationSpec = spring()) }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                onClickLabel = "Animated button",
                indication = null,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {

            drawRoundRect(
                color = buttonColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )

            if (isOutline) {
                drawRoundRect(
                    color = borderColor,
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(
                    horizontal = 15.dp,
                    vertical = 11.dp
                ),
            text = text,
            color = buttonTextColor
        )
    }
}

/**
 * A class containing the background and text colors of the button in different states.
 * @param inactiveColor Button color when inactive.
 * @param pressedColor Button color when pressed.
 * @param disableColor Button color when disabled.
 * @param textColor Button text color.
 * */
@Immutable
class ZephyrButtonColor(
    val inactiveColor: Color = TertiaryOne,
    val pressedColor: Color = PrimaryColor,
    val disableColor: Color = DisableColor,
    val textColor: Color = Color.White
) {

    fun copy(
        inactiveColor: Color = this.inactiveColor,
        pressedColor: Color = this.pressedColor,
        disableColor: Color = this.pressedColor,
        textColor: Color = this.textColor
    ) = ZephyrButtonColor(
        inactiveColor.takeOrElse { this.inactiveColor },
        pressedColor.takeOrElse { this.pressedColor },
        disableColor.takeOrElse { this.disableColor },
        textColor.takeOrElse { this.textColor },
    )

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || other !is ZephyrButtonColor) return false

        if (inactiveColor != other.inactiveColor) return false
        if (pressedColor != other.pressedColor) return false
        if (disableColor != other.disableColor) return false
        if (textColor != other.textColor) return false

        return true
    }

    override fun hashCode(): Int {

        var result = inactiveColor.hashCode()

        result = 31 * result + pressedColor.hashCode()
        result = 31 * result + disableColor.hashCode()
        result = 31 * result + textColor.hashCode()

        return result
    }
}