package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp

@Composable
fun DismissText(
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    Text(
        text = "CineView 4.0",
        color = Color.White,
        fontSize = 30.sp,
        modifier = Modifier.graphicsLayer {
            alpha = 1 - if (indexProvider() == 0) scrollProvider().toFloat() / 100F else 0F
        }
    )
}
