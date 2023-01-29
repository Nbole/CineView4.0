package com.example.baseapp.domain

import com.example.baseapp.data.remote.SerialResponse
import com.example.baseapp.data.remote.model.MovieResult

interface MovieDataSource {
    suspend fun getLatestMovies(): SerialResponse<MovieResult>
    suspend fun getMovie(query: String): SerialResponse<MovieResult>
}
