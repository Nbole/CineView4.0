package com.example.baseapp.presentation.ui.compose.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SearchModel
import com.example.baseapp.presentation.ui.compose.core.Paragraphs.ParagraphLight
import com.example.baseapp.presentation.ui.compose.ui.preview_providers.SearchModelProvider

@Composable
fun TextFieldComposable(
    searchModel: () -> SearchModel,
    scrollProvider: () -> Int,
    indexProvider: () -> Int,
) {
    Box {
        CollapsingTextField(searchModel, { scrollProvider() }, { indexProvider() })
        if (searchModel.invoke().errorResource != null) {
            ParagraphLight(
                modifier = Modifier.padding(start = 16.dp),
                stringRes = searchModel.invoke().errorResource!!,
                paragraphSize = Paragraphs.ParagraphSize.PARAGRAPH_12_SP,
                color = colorResource(id = R.color.red_ff2d31)
            )
        }
    }
}

@Preview
@Composable
fun TextFieldComposablePreview(
    @PreviewParameter(SearchModelProvider::class) previewParameter: SearchModel,
) {
    MaterialTheme {
        TextFieldComposable(
            { previewParameter },
            { 1 },
            { 1 }
        )
    }
}
