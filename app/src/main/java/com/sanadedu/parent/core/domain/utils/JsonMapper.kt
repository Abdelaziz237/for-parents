package com.sanadedu.parent.core.domain.utils

import com.google.gson.Gson
import com.sanadedu.parent.core.domain.model.ConversionResult
import kotlinx.serialization.SerializationException

fun <T> decodeJsonFromString(json: String, dtoClass: Class<T>): ConversionResult<T> {
    val gson = Gson()

    return try {
        val decodedData: T = gson.fromJson(json, dtoClass)
        ConversionResult.Success(data = decodedData) // Use generic Success type
    } catch (e: SerializationException) {
        // Handle parsing errors more informatively
        ConversionResult.Error(e.message ?: "Unknown error") // Provide the exception as the cause
    }
}