package com.martini.dartplayer.common

sealed class Resource<T>(val data: T? = null, val error: T? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: T, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}