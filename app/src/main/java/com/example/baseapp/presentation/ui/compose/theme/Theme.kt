package com.example.baseapp.presentation.ui.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.baseapp.R

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

object Styles {
    @Composable
    fun editTextStyle() = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.grey_989fb3),
        backgroundColor = Color.Transparent,
        cursorColor = colorResource(id = R.color.grey_989fb3),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
    )
}