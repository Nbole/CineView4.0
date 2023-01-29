package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteMovieEntity(
    @PrimaryKey val movieId: Int
)
