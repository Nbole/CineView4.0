package com.example.baseapp.domain.usecase

import com.example.baseapp.domain.MovieRepository
import javax.inject.Inject

interface UpdateMovieUseCase {
    suspend fun updateFavoriteMovie(id: Int)
}

class UpdateMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
): UpdateMovieUseCase {
    override suspend fun updateFavoriteMovie(id: Int) {
        movieRepository.updateFavoriteMovie(id)
    }
}
