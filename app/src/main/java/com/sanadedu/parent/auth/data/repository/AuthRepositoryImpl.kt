package com.sanadedu.parent.auth.data.repository

import android.util.Log
import com.google.gson.Gson
import com.sanadedu.parent.auth.data.body_dtos.OtpVerificationBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.ResendOtpBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.ResetPasswordLinkBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.SignInBodyDTO
import com.sanadedu.parent.auth.domain.model.User
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.core.data.NetworkFailedCodes.BAD_REQUEST
import com.sanadedu.parent.core.data.NetworkFailedCodes.EMAIL_EXISTS
import com.sanadedu.parent.core.data.NetworkFailedCodes.NOT_CORRECT_OTP
import com.sanadedu.parent.core.data.NetworkFailedCodes.NOT_FOUND
import com.sanadedu.parent.core.data.NetworkFailedCodes.UNKNOWN
import com.sanadedu.parent.core.data.NetworkRoutes.AUTH
import com.sanadedu.parent.core.data.NetworkRoutes.BASE_URL
import com.sanadedu.parent.core.data.NetworkRoutes.FORGOT_PASSWORD
import com.sanadedu.parent.core.data.NetworkRoutes.LOGIN
import com.sanadedu.parent.core.data.NetworkRoutes.PARENTS
import com.sanadedu.parent.core.data.NetworkRoutes.REGISTER
import com.sanadedu.parent.core.data.NetworkRoutes.RESEND_OTP
import com.sanadedu.parent.core.data.NetworkRoutes.VERIFY_EMAIL
import com.sanadedu.parent.core.data.NetworkRoutes.VERIFY_OTP
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

class AuthRepositoryImpl(
    private val ktorClient: HttpClient = KtorClient.client,
    private val gson: Gson = Gson()
): AuthRepository {
    @OptIn(InternalAPI::class)
    override suspend fun signIn(bodyValue: SignInBodyDTO): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.post(BASE_URL + PARENTS + AUTH + LOGIN) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }

            Log.e("Add Student", response.toString())
            Log.e("Add Student", jsonBody)
            Log.e("Add Student", response.status.value.toString())
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

    @OptIn(InternalAPI::class)
    override suspend fun verifyEmail(bodyValue: User.UserForVerify): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.post(BASE_URL + PARENTS + AUTH + VERIFY_EMAIL) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }

            Log.e("Auth", response.toString())
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())
            Log.e("Auth", response.request.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(EMAIL_EXISTS)
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

    @OptIn(InternalAPI::class)
    override suspend fun verifyOtp(bodyValue: OtpVerificationBodyDTO): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.patch(BASE_URL + PARENTS + AUTH + VERIFY_OTP) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }
            Log.e("Auth", response.toString())
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())
            Log.e("Auth", response.request.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(UNKNOWN)
                }
                401 -> {
                    ApiResponse.Failed(NOT_CORRECT_OTP)
                }
                403 -> {
                    ApiResponse.Refused
                }
                NOT_FOUND -> {
                    ApiResponse.Failed(NOT_FOUND)
                }
                else -> {
                    ApiResponse.Failed(UNKNOWN)
                }
            }
        } catch (e: Exception) {
            // Handle errors here
            Log.e("Add", "error: ${e.message}")
            ApiResponse.Error
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun resendOtp(bodyValue: ResendOtpBodyDTO): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.patch(BASE_URL + PARENTS + AUTH + RESEND_OTP) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }
            Log.e("Auth", response.toString())
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())
            Log.e("Auth", response.request.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(UNKNOWN)
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


    @OptIn(InternalAPI::class)
    override suspend fun register(bodyValue: User.UserForRegister): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.patch(BASE_URL + PARENTS + AUTH + REGISTER) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }

            Log.e("Auth", response.toString())
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())
            Log.e("Auth", response.request.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(UNKNOWN)
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

    @OptIn(InternalAPI::class)
    override suspend fun resetPassword(bodyValue: ResetPasswordLinkBodyDTO): ApiResponse {
        val jsonBody = gson.toJson(bodyValue)
        return try {
            val response = ktorClient.patch(BASE_URL + PARENTS + AUTH + FORGOT_PASSWORD) {
                contentType(ContentType.Application.Json)
                body = TextContent(jsonBody, ContentType.Application.Json)
            }

            Log.e("Auth", response.toString())
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())
            Log.e("Auth", response.request.toString())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                BAD_REQUEST -> {
                    ApiResponse.Failed(UNKNOWN)
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