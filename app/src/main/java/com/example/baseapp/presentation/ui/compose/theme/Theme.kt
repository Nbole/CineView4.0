package com.example.baseapp.presentation.ui.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.baseapp.R
import com.example.baseapp.presentation.ui.compose.MTypography
import com.example.baseapp.presentation.ui.compose.Shapes

private val LightColorPalette = lightColors(
    primary = Yellow,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun DesignTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = MTypography,
        shapes = Shapes,
        content = content
    )
}