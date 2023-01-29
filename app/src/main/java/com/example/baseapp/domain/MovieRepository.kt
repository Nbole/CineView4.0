package com.example.baseapp.domain

import com.example.baseapp.domain.model.vo.DomainResponse
import com.example.baseapp.domain.model.vo.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getLatestMovies(): Flow<DomainResponse<List<MovieResult>>>
    fun getFavoritesMovies(): Flow<List<MovieResult>>
    fun searchMovies(query: String): Flow<DomainResponse<List<MovieResult>>>
    suspend fun updateFavoriteMovie(id: Int)
}