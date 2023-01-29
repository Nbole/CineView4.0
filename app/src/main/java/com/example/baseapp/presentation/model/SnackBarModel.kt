package com.example.baseapp.presentation.model

data class SnackBarModel(
    val addOrRemoveMovie: Boolean?,
    val action: () -> Unit,
)
