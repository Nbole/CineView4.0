package com.example.baseapp.data.remote

sealed class SerialResponse<T> {
    data class Success<T>(val data: T) : SerialResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : SerialResponse<T>()
}
