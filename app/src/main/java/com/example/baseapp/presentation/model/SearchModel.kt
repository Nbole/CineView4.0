package com.example.baseapp.presentation.model

import androidx.annotation.StringRes

data class SearchModel(
    @StringRes val errorResource: Int?,
    @StringRes val titleResource: Int,
    @StringRes val labelResource: Int,
    @StringRes val validateResource: Int?,
    val enabled: Boolean,
    val validateOrRemove: Boolean,
    val searchText: String?,
    val searchActionable: (String) -> Unit,
    val acceptActionable: (String) -> Unit,
)
