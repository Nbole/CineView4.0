package com.example.baseapp.domain.usecase

import com.example.baseapp.data.di.DispatchersProvider
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieUseCase {
    fun getLatestMovies(): Flow<DomainResponse<List<MovieResult>>>
}

class MoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatchersProvider
): MovieUseCase {
    override fun getLatestMovies(): Flow<DomainResponse<List<MovieResult>>> =
       movieRepository.getLatestMovies().flowOn(dispatcher.io)
}