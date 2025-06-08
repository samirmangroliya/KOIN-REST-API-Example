package com.samir.koinrestapiexample.presentation.utils

sealed class NetworkResult<out R> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error<T>(val message: String?, val exception: Exception): NetworkResult<T>()
    data object Loading: NetworkResult<Nothing>()
}