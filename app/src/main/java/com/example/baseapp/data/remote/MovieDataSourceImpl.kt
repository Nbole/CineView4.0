package com.example.baseapp.data.remote

import com.example.baseapp.BuildConfig
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.domain.MovieDataSource
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException

class MovieDataSourceImpl : MovieDataSource {
    override suspend fun getLatestMovies(): SerialResponse<MovieResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url("https://api.themoviedb.org/3/movie/popular")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }

    override suspend fun getMovie(query: String): SerialResponse<MovieResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url("https://api.themoviedb.org/3/search/movie")
                    parameter("query", query)
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }
}
