package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp

@Composable
fun CollapsingToolBar(
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
    modifier: Modifier = Modifier,
    defaultSize: Dp,
    content: @Composable () -> Unit,
) {
    val collapseRange: Float = with(LocalDensity.current) { (defaultSize - 55.dp).toPx() }
    Layout(
        modifier = modifier,
        content = content
    ) { measureables, constraints ->
        check(measureables.size == 1)
        val s: Float = if (indexProvider() == 0) scrollProvider().toFloat() else 0F
        val collapseFractionProvider = (s / collapseRange).coerceIn(0f, 1f)

        val imageY = if (indexProvider() >= 1) {
            55.dp.roundToPx()
        } else {
            lerp(defaultSize, 55.dp, collapseFractionProvider).roundToPx()
        }

        val imgHeight = if (indexProvider() >= 1) {
            0.dp.roundToPx()
        } else {
            lerp(40.dp, 0.dp, collapseFractionProvider).roundToPx()
        }

        val imagePlaceable =
            measureables[0].measure(Constraints.fixed(constraints.maxWidth, 80.dp.roundToPx()))

        val imageX = lerp(
            0.dp,
            (constraints.maxWidth).toDp() - 180.dp,
            if (indexProvider() == 0) collapseFractionProvider else 1f
        )
        layout(
            width = constraints.maxWidth,
            height = imageY
        ) {
            imagePlaceable.placeRelative(imageX.roundToPx(), imgHeight)
        }
    }
}
