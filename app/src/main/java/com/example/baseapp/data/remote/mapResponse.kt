package com.example.baseapp.data.remote

import com.example.baseapp.domain.model.vo.DomainResponse

internal fun <I, O> NWResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is NWResponse.Error -> {
            DomainResponse.Error(message)
        }
        is NWResponse.Loading -> {
            DomainResponse.Loading(data?.let(transformAction::invoke))
        }
        is NWResponse.Success -> {
            DomainResponse.Success(transformAction.invoke(data))
        }
    }

sealed class NWResponse<T> {
    data class Success<T>(val data: T) : NWResponse<T>()
    data class Loading<T>(val data: T? = null) : NWResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : NWResponse<T>()
}
