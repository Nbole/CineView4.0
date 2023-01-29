package com.example.baseapp.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseapp.data.local.model.dao.MoviesDao
import com.example.baseapp.data.local.model.db.FavouriteMovieEntity
import com.example.baseapp.data.local.model.db.MovieEntity
import com.example.baseapp.data.local.model.db.SearchMovieEntity

@Database(
    entities = [MovieEntity::class, FavouriteMovieEntity::class, SearchMovieEntity::class],
    version = 1
)
abstract class MovieDb : RoomDatabase() {
   abstract fun moviesDao(): MoviesDao

    companion object {
        const val DATABASE_NAME: String = "movies"
    }
}
