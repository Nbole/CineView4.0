package com.example.baseapp.data.local.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Movie(
    val id: Int,
    @SerialName("original_title") val title: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("overview") val overView: String?,
)
