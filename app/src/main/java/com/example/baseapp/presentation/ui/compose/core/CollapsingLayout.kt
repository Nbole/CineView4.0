package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp

@Composable
fun CollapsingLayout(
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val collapseRange: Float = with(LocalDensity.current) { (60.dp - 10.dp).toPx() }
    Layout(
        modifier = modifier,
        content = content
    ) { measureables, constraints ->
        check(measureables.size == 1)
        val s: Float = if (indexProvider() == 0) scrollProvider().toFloat() else 0F
        val collapseFractionProvider = (s / collapseRange).coerceIn(0f, 1f)

        val imageY = if (indexProvider() >= 1) {
            10.dp.roundToPx()
        } else {
            lerp(60.dp, 10.dp, collapseFractionProvider).roundToPx()
        }

        val imagePlaceable = measureables[0].measure(
            Constraints.fixed(constraints.maxWidth, 50.dp.roundToPx())
        )

        layout(
            width = constraints.maxWidth,
            height = imageY
        ) {
            imagePlaceable.placeRelative(0.dp.roundToPx(), imageY - 50.dp.roundToPx())
        }
    }
}
