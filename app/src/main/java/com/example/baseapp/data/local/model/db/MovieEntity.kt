package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val posterPath: String?,
    val isFavourite: Boolean,
    val releaseDate: String?,
    val overView: String?,
)
