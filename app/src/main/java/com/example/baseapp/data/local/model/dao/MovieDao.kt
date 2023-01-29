package com.example.baseapp.data.local.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.baseapp.data.local.model.db.FavouriteMovieEntity
import com.example.baseapp.data.local.model.db.MovieEntity
import com.example.baseapp.data.local.model.db.SearchMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSearchMovies(movies: List<SearchMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavouritesMovies(movies: List<FavouriteMovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    fun loadMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM FavouriteMovieEntity")
    fun loadFavouriteMovieEntities(): Flow<List<FavouriteMovieEntity>>

    @Transaction
    suspend fun updateFavoriteMovies(id: Int) {
        val favouriteMovieEntities = loadFavouriteMovieEntities().firstOrNull()
        deleteFavouriteMovieEntity()
        val favourites = if (favouriteMovieEntities.orEmpty().contains(FavouriteMovieEntity(id))) {
            favouriteMovieEntities.orEmpty().filter { it.movieId != id }
        } else {
            favouriteMovieEntities.orEmpty().plus(FavouriteMovieEntity(id))
        }
        saveFavouritesMovies(favourites)
        val movies: List<MovieEntity> = loadMovies().firstOrNull().orEmpty()
        deleteMovies()
        saveMovies(
            movies.map {
                if (it.id == id) {
                    it.copy(isFavourite = favourites.map { movie -> movie.movieId }.contains(it.id))
                } else {
                    it
                }
            }
        )
    }

    @Query("""SELECT * FROM MovieEntity INNER JOIN FavouriteMovieEntity ON id = movieId""")
    fun loadFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("""SELECT * FROM FavouriteMovieEntity """)
    fun loadFavoriteMoviesIds(): Flow<List<FavouriteMovieEntity>>

    @Query("""SELECT * FROM MovieEntity INNER JOIN SearchMovieEntity ON id = movieId WHERE `query` ==:query""")
    fun loadSearchMovies(query: String): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity")
    fun deleteMovies()

    @Query("DELETE FROM SearchMovieEntity")
    fun deleteSearchMovies()

    @Query("DELETE FROM FavouriteMovieEntity")
    fun deleteFavouriteMovieEntity()
}
