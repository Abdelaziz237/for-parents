package com.sanadedu.parent.profile.data.repository

import android.util.Log
import com.sanadedu.parent.core.data.NetworkFailedCodes.UNKNOWN
import com.sanadedu.parent.core.data.NetworkRoutes.BASE_URL
import com.sanadedu.parent.core.data.NetworkRoutes.DETAILS
import com.sanadedu.parent.core.data.NetworkRoutes.PARENTS
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import com.sanadedu.parent.profile.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProfileRepositoryImpl(
    private val ktorClient: HttpClient = KtorClient.client,
    private val authToken: String,
)
    : ProfileRepository {
    override suspend fun getProfileInfo(): ApiResponse {
        return try {
            val response = ktorClient.get(
                BASE_URL + PARENTS + DETAILS
            ) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("profileInfo", response.request.toString())
            Log.e("profileInfo", response.bodyAsText())

            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                401 -> {
                    ApiResponse.Unauthorized
                }
                403 -> {
                    ApiResponse.Refused
                }
                else -> {
                    ApiResponse.Failed(UNKNOWN)
                }
            }
        } catch (e: Exception) {
            ApiResponse.Error
        }
    }

    override suspend fun deleteAccount(): ApiResponse {
        return try {
            val response = ktorClient.delete(
                BASE_URL + PARENTS
            ) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("deleteAccount", response.request.toString())
            Log.e("deleteAccount", response.bodyAsText())

            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                401 -> {
                    ApiResponse.Unauthorized
                }
                403 -> {
                    ApiResponse.Refused
                }
                else -> {
                    ApiResponse.Failed(UNKNOWN)
                }
            }
        } catch (e: Exception) {
            ApiResponse.Error
        }
    }
}