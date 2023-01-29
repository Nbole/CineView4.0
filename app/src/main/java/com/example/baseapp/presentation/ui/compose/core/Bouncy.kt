package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick(state: Boolean) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed && state) {
            0.80f
        } else {
            1f
        }
    )

    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }.pointerInput(buttonState) {
        awaitPointerEventScope {
            buttonState = if (buttonState == ButtonState.Pressed) {
                waitForUpOrCancellation()
                ButtonState.Idle
            } else {
                awaitFirstDown(false)
                ButtonState.Pressed
            }
        }
    }
}