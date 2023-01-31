package com.example.baseapp.domain.usecase

import com.example.baseapp.data.di.DispatchersProvider
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface FavoriteUseCase {
    fun getFavorites(): Flow<List<MovieResult>>
}

class FavoriteUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatchersProvider
): FavoriteUseCase {
    override fun getFavorites(): Flow<List<MovieResult>> =
       movieRepository.getFavoritesMovies().flowOn(dispatcher.io)
}