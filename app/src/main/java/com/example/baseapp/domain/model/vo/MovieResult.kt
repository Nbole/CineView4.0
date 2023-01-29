package com.example.baseapp.domain.model.vo

data class MovieResult(
    val id: Int,
    val title: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val overview: String?,
    val isFavourite: Boolean,
)
