package com.example.baseapp.domain.usecase

import com.example.baseapp.data.di.DispatchersProvider
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchUseCase {
    fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>>
}

class SearchUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatchersProvider
) : SearchUseCase {
    override fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>> =
        if (query.isEmpty()) {
            movieRepository.getLatestMovies()
        } else {
            movieRepository.searchMovies(query)
        }.flowOn(dispatcher.io)
}
