package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["movieId","query"])
data class SearchMovieEntity(
    val movieId: Int,
    val query: String
)
