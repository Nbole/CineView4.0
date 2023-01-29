package com.example.baseapp.presentation.ui.compose.core

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SearchModel
import com.example.baseapp.presentation.ui.compose.core.Paragraphs.ParagraphLight
import com.example.baseapp.presentation.ui.compose.core.Styles.editTextStyle
import com.example.baseapp.presentation.ui.compose.theme.GreenB

@Composable
fun TextFieldComposable(
    searchModel: SearchModel,
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    Box {
        SideEffect { Log.d("NB", " TextFieldComposable recomposition") }
        CollapsingTextField(searchModel, { scrollProvider() }, { indexProvider() })
        if (searchModel.errorResource != null) {
            ParagraphLight(
                modifier = Modifier.padding(start = 16.dp),
                stringRes = searchModel.errorResource,
                paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_12_SP,
                color = colorResource(id = R.color.red_ff2d31)
            )
        }
    }
}

@Composable
fun CollapsingTextField(
    searchModel: SearchModel,
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    SideEffect { Log.d("NB", " TextFieldComposable2 recomposition") }
    CollapsingLayout(
        scrollProvider = scrollProvider,
        indexProvider = indexProvider,
        defaultSize = 48.dp
    ) {
        val collapseRange: Float = with(LocalDensity.current) { (48.dp - 0.dp).toPx() }
        TextField(
            singleLine = true,
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .clickable(enabled = searchModel.enabled, null, null) {}
                .background(
                    colorResource(
                        if (!searchModel.searchText.isNullOrEmpty()) {
                            R.color.white_ffffff
                        } else {
                            R.color.grey_f6f7fc
                        }
                    )
                )
                .fillMaxWidth()
                .graphicsLayer {
                    val s: Float = if (indexProvider() == 0) scrollProvider().toFloat() else 0F
                    val collapseFractionProvider = (s/collapseRange).coerceIn(0f, 1f)
                    alpha = 1f - collapseFractionProvider
                }
                .border(width = 0.dp, color = GreenB, shape = RoundedCornerShape(6.dp)),
            value = searchModel.searchText.orEmpty(),
            onValueChange = {
                searchModel.searchActionable(it)
            },
            label = {
                ParagraphLight(color = colorResource(id = if (searchModel.errorResource != null) {
                    R.color.red_ff2d31
                } else {
                    R.color.grey_989fb3
                }),
                    text = stringResource(id = searchModel.labelResource),
                    paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_14_SP)
            },
            leadingIcon = {
                NonClickableIcon(icon = R.drawable.ic_baseline_search_24,
                    iconColor = colorResource(id = R.color.grey_a2a8bc))
            },
            trailingIcon = {
                ParagraphLight(modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        searchModel.acceptActionable.invoke(searchModel.searchText.orEmpty())
                    },
                    text = stringResource(id = R.string.accept),
                    paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_12_SP,
                    color = colorResource(id = R.color.green_20bf62))
            },
            colors = editTextStyle()
        )
    }
}

@Composable
fun CollapsingLayout(
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
    modifier: Modifier = Modifier,
    defaultSize: Dp,
    content: @Composable () -> Unit,
) {
    val collapseRange: Float = with(LocalDensity.current) { (48.dp - 0.dp).toPx() }
    SideEffect { Log.d("NB", "collapsingLayout recompose") }
    Layout(
        modifier = modifier,
        content = content
    ) { measureables, constraints ->
        check(measureables.size == 1)
        val s: Float = if (indexProvider() == 0) scrollProvider().toFloat() else 0F
        val collapseFractionProvider = (s/collapseRange).coerceIn(0f, 1f)

        val imageY = if (indexProvider() >= 1 ) {
            0.dp.roundToPx()
        } else {
            lerp(defaultSize, 0.dp, collapseFractionProvider).roundToPx()
        }
        val imagePlaceable = measureables[0].measure(Constraints.fixed(constraints.maxWidth, imageY))

        layout(
            width = constraints.maxWidth,
            height = imageY
        ) {
            imagePlaceable.placeRelative(0.dp.roundToPx(), 0.dp.roundToPx())
        }
    }
}

object Styles {
    @Composable
    fun editTextStyle() = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.grey_989fb3),
        backgroundColor = Color.Transparent,
        cursorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
    )
}

