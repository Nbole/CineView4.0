package com.example.baseapp.presentation.ui.compose.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun ClickableIcon(
    modifier: Modifier,
    @DrawableRes icon: Int,
    iconColor: Color? = null,
    contentDescription: String? = null,
    action: () -> Unit,
) {
    if (iconColor != null) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = modifier
                .size(24.dp)
                .clickable {
                    action.invoke()
                }
        )
    } else {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = modifier
                .size(24.dp)
                .clickable {
                    action.invoke()
                },
            tint = Color.Unspecified
        )
    }
}
