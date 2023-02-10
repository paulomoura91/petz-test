package com.paulomoura.petztest.commons

sealed class Response<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Loading<T> : Response<T>()
    class Error<T>(data: T? = null, error: Throwable) : Response<T>(data, error)
}