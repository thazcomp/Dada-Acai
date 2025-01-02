package com.taptwotimes.dadaacai.data.results

sealed class APIResult<out R> {
    data class Success<out T>(val data: T) : APIResult<T>()
    data class Error(val exception: Exception) : APIResult<Nothing>()
    data object Loading : APIResult<Nothing>()
}