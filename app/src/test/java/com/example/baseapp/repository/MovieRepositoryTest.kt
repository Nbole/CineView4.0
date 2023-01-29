package com.example.baseapp.repository

import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class MovieRepositoryTest : MovieRepository {
    private val moviesFlow: MutableSharedFlow<DomainResponse<List<MovieResult>>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val favouriteFlow: MutableSharedFlow<List<MovieResult>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun send(input: DomainResponse<List<MovieResult>>) { moviesFlow.tryEmit(input) }

    fun send(input: List<MovieResult>) { favouriteFlow.tryEmit(input) }

    override fun getLatestMovies(): Flow<DomainResponse<List<MovieResult>>> = moviesFlow

    override fun getFavoritesMovies(): Flow<List<MovieResult>> = favouriteFlow

    override fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>> = moviesFlow

    override suspend fun updateFavoriteMovie(id: Int) {}
}
