package com.example.baseapp.presentation.ui.compose.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseapp.R

@Composable
fun NonClickableIcon(
    @DrawableRes icon: Int,
    iconColor: Color? = null,
    contentDescription: String? = null,
) {
    Box(modifier = Modifier.size(24.dp)) {
        if (iconColor != null) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = iconColor,
            )
        } else {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
            )
        }
    }
}

@Preview
@Composable
fun NonClickableIconPreview() {
    MaterialTheme {
        NonClickableIcon(icon = R.drawable.baseline_favorite_24)
    }
}
