package com.example.baseapp.presentation.ui.compose.ui.preview_providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.baseapp.R
import com.example.baseapp.presentation.model.SearchModel

class SearchModelProvider : PreviewParameterProvider<SearchModel> {
    override val values: Sequence<SearchModel> = sequenceOf(
        SearchModel(
            errorResource = null,
            titleResource = R.string.accept,
            labelResource = R.string.accept,
            validateResource = R.string.accept,
            enabled = true,
            validateOrRemove = true,
            searchText = null,
            searchActionable = {},
            acceptActionable = {}
        )
    )
}
