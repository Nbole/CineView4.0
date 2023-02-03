package com.example.baseapp.domain.usecase

import com.example.baseapp.data.di.DispatchersProvider
import com.example.baseapp.data.remote.NWResponse
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteUseCase {
    fun getFavorites(): Flow<DomainResponse<List<MovieResult>>>
}

class FavoriteUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatchersProvider,
) : FavoriteUseCase {
    override fun getFavorites(): Flow<DomainResponse<List<MovieResult>>> =
        movieRepository.getFavoritesMovies().map {
            when (it) {
                is NWResponse.Success -> DomainResponse.Success(
                        it.data
                    )
                is NWResponse.Error -> DomainResponse.Error(
                        message = "Error"
                    )
                else -> DomainResponse.Loading()
            }
        }.flowOn(dispatcher.io)
}