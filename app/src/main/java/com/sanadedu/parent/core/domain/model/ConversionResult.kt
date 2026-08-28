package com.sanadedu.parent.core.domain.model

sealed class ConversionResult<T> {
    data class Success<T>(val data: T) : ConversionResult<T>()
    data class Error<T>(val exception: String) : ConversionResult<T>()
}