package com.sanadedu.parent.auth.domain.util

import android.service.autofill.UserData
import android.util.Log
import com.sanadedu.parent.auth.domain.dto.SignInResponseDTO
import com.sanadedu.parent.auth.domain.dto.SignUpResponseDTO
import com.sanadedu.parent.core.domain.model.ConversionResult
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlin.math.log

fun decodeSignInResponse(json: String): ConversionResult<SignInResponseDTO> {
    return try {
        val decodedData: SignInResponseDTO = decodeFromString(json)
        ConversionResult.Success(data = decodedData) // Use generic Success type
    } catch (e: SerializationException) {
        // Handle parsing errors more informatively
        ConversionResult.Error(e.message ?: "Unknown error") // Provide the exception as the cause
    }
}

fun decodeSignUpResponse(json: String): ConversionResult<SignUpResponseDTO> {
    return try {
        Log.e("json", json)
        val decodedData: SignUpResponseDTO = decodeFromString(json)
        ConversionResult.Success(data = decodedData) // Use generic Success type
    } catch (e: SerializationException) {
        // Handle parsing errors more informatively
        ConversionResult.Error(e.message ?: "Unknown error") // Provide the exception as the cause
    }
}