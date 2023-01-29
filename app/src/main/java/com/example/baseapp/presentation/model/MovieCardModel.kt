package com.example.baseapp.presentation.model

data class MovieCardModel(
    val id: Int,
    val text: String?,
    val poster: String?,
    val releaseDate: String?,
    val overView: String?,
    val isFavourite: Boolean,
    val mustShowDetail: Boolean,
    val showDetail: (Int) -> Unit,
    val actionable: (Int, Boolean) -> Unit,
)
