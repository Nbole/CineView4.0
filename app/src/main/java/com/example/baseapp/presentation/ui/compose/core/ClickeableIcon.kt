package com.example.baseapp.presentation.ui.compose.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseapp.R

@Composable
internal fun ClickableIcon(
    modifier: Modifier,
    @DrawableRes icon: Int,
    iconColor: Color? = null,
    contentDescription: String? = null,
    action: () -> Unit,
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        tint = iconColor ?: Color.Unspecified,
        modifier = modifier
            .size(24.dp)
            .clickable {
                action.invoke()
            }
    )
}

@Preview(showBackground = true)
@Composable
fun ClickableIconPreview() {
    MaterialTheme {
        ClickableIcon(
            Modifier,
            R.drawable.ic_baseline_favorite_border_24,
        ) {}
    }
}
