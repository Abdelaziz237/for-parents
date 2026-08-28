package com.sanadedu.parent.auth.domain.util

import android.util.Log
import com.sanadedu.parent.auth.domain.dto.PreSignedUrlDTO
import com.sanadedu.parent.core.data.NetworkFailedCodes.BAD_REQUEST
import com.sanadedu.parent.core.data.NetworkFailedCodes.NOT_FOUND
import com.sanadedu.parent.core.data.NetworkFailedCodes.UNKNOWN
import com.sanadedu.parent.core.data.NetworkRoutes.BASE_URL
import com.sanadedu.parent.core.data.NetworkRoutes.IMAGES
import com.sanadedu.parent.core.data.NetworkRoutes.UPLOAD
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

object PreSignedUrlFetcher {
    private val ktorClient = KtorClient.client


    private val validator: DataValidator<PreSignedUrlDTO> = DataValidator(
        dtoClass = PreSignedUrlDTO::class.java
    )

    suspend fun fetch(email: String): ValidationStatus<PreSignedUrlDTO> {
        val response = getPreSignedUrl(email)
        return validator.validateData(response)
    }

    private suspend fun getPreSignedUrl(email: String): ApiResponse {
        return try {
            val response = ktorClient.post(BASE_URL + UPLOAD + IMAGES) {
                contentType(ContentType.Application.Json)
//                body = TextContent(jsonBody, ContentType.Application.Json)
            }

            Log.e("uploadImage", response.toString())
//            Log.e("uploadImage", jsonBody)
            Log.e("uploadImage", response.status.value.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(BAD_REQUEST)
                }
                401 -> {
                    // TODO("Re-Login")
                    ApiResponse.Unauthorized
                }
                403 -> {
                    // TODO("say refused")
                    ApiResponse.Refused
                }
                NOT_FOUND -> {
                    ApiResponse.Failed(NOT_FOUND)
                }
                else -> {
                    // TODO("say failed")
                    ApiResponse.Failed(UNKNOWN)
                }
            }
        } catch (e: Exception) {
            // Handle errors here
            Log.e("Add", "error: ${e.message}")
            ApiResponse.Error
        }
    }
}