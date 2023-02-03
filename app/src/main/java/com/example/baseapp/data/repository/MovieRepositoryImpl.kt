package com.example.baseapp.data.repository

import com.example.baseapp.data.local.model.dao.MoviesDao
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.local.model.db.MovieEntity
import com.example.baseapp.data.local.model.db.SearchMovieEntity
import com.example.baseapp.data.local.networkBoundResource
import com.example.baseapp.data.remote.NWResponse
import com.example.baseapp.data.remote.SerialResponse
import com.example.baseapp.data.remote.mapResponse
import com.example.baseapp.domain.MovieDataSource
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val db: MoviesDao,
    private val movieDataSource: MovieDataSource,
) : MovieRepository {
    override fun getLatestMovies(): Flow<DomainResponse<List<MovieResult>>> =
        networkBoundResource(
            { db.loadMovies() },
            { movieDataSource.getLatestMovies() },
            { response ->
                val movies: List<Movie> = (response as SerialResponse.Success).data.results
                val favourites: List<Int> =
                    db.loadFavoriteMoviesIds().firstOrNull().orEmpty().map { it.movieId }
                db.deleteMovies()
                if (movies.isNotEmpty()) {
                    db.saveMovies(
                        movies.map {
                            MovieEntity(
                                id = it.id,
                                title = it.title,
                                posterPath = it.posterPath,
                                isFavourite = favourites.contains(it.id),
                                releaseDate = it.releaseDate,
                                overView = it.overView
                            )
                        }
                    )
                }
            }
        ).map { w ->
            w.mapResponse { p ->
                p.map { movieEntity ->
                    MovieResult(
                        id = movieEntity.id,
                        title = movieEntity.title,
                        posterPath = movieEntity.posterPath,
                        isFavourite = movieEntity.isFavourite,
                        releaseDate = movieEntity.releaseDate,
                        overview = movieEntity.overView
                    )
                }
            }
        }

    override fun getFavoritesMovies(): Flow<NWResponse<List<MovieResult>>> =
        networkBoundResource(
            {
                db.loadFavoriteMovies().map { w ->
                    w.map {
                        MovieResult(
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath,
                            isFavourite = true,
                            releaseDate = it.releaseDate,
                            overview = it.overView
                        )
                    }
                }
            },
            { movieDataSource.getLatestMovies() },
            {},
            { false }
        )

    override fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>> =
        networkBoundResource(
            { db.loadSearchMovies(query) },
            { movieDataSource.getMovie(query) },
            { response ->
                val movies: List<Movie> = (response as SerialResponse.Success).data.results
                db.deleteSearchMovies()
                if (movies.isNotEmpty()) {
                    val favourites: List<Int> =
                        db.loadFavoriteMoviesIds().firstOrNull().orEmpty().map { it.movieId }
                    db.saveSearchMovies(
                        movies.map { movie ->
                            SearchMovieEntity(
                                movieId = movie.id,
                                query = query
                            )
                        }
                    )
                    db.saveMovies(
                        movies.map {
                            MovieEntity(
                                id = it.id,
                                title = it.title,
                                posterPath = it.posterPath,
                                isFavourite = favourites.contains(it.id),
                                releaseDate = it.releaseDate,
                                overView = it.overView
                            )
                        }
                    )
                } else {
                    db.deleteMovies()
                }
            }
        ).map { w ->
            w.mapResponse { p ->
                p.map {
                    MovieResult(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath,
                        isFavourite = it.isFavourite,
                        releaseDate = it.releaseDate,
                        overview = it.overView
                    )
                }
            }
        }

    override suspend fun updateFavoriteMovie(id: Int) {
        db.updateFavoriteMovies(id)
    }
}
