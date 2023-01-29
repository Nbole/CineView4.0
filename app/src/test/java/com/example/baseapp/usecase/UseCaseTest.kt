package com.example.baseapp.usecase

import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import com.example.baseapp.domain.usecase.FavoriteUseCase
import com.example.baseapp.domain.usecase.SearchUseCase
import com.example.baseapp.domain.usecase.UpdateMovieUseCase
import com.example.baseapp.repository.MovieRepositoryTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdateMovieUseCaseTest : UpdateMovieUseCase {
    override suspend fun updateFavoriteMovie(id: Int) {}
}

class SearchUseCaseTest(private val movieRepositoryTest: MovieRepositoryTest) : SearchUseCase {
    override fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>> =
        movieRepositoryTest.getLatestMovies().flowOn(Dispatchers.IO)
}

class FavouriteUseCaseTest(private val movieRepositoryTest: MovieRepositoryTest): FavoriteUseCase {
    override fun getFavorites(): Flow<List<MovieResult>> =
        movieRepositoryTest.getFavoritesMovies().flowOn(Dispatchers.IO)
}
