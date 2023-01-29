package com.example.baseapp.domain.model.vo

sealed class DomainResponse<T> {
    data class Success<T>(val data: T) : DomainResponse<T>()
    data class Loading<T>(val data: T? = null) : DomainResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : DomainResponse<T>()
}