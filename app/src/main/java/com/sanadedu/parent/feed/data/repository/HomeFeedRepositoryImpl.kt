package com.sanadedu.parent.feed.data.repository

import android.util.Log
import com.google.gson.Gson
import com.sanadedu.parent.core.data.NetworkFailedCodes.UNKNOWN
import com.sanadedu.parent.core.data.NetworkRoutes.ATTENDANCE
import com.sanadedu.parent.core.data.NetworkRoutes.BASE_URL
import com.sanadedu.parent.core.data.NetworkRoutes.CARD
import com.sanadedu.parent.core.data.NetworkRoutes.CARDS
import com.sanadedu.parent.core.data.NetworkRoutes.GROUPS
import com.sanadedu.parent.core.data.NetworkRoutes.PARENTS
import com.sanadedu.parent.core.data.NetworkRoutes.STUDENT
import com.sanadedu.parent.core.data.NetworkRoutes.STUDENTS
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import com.sanadedu.parent.feed.domain.repository.HomeFeedRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

class HomeFeedRepositoryImpl(
    private val authToken: String,
    private val gson: Gson = Gson(),
    private val ktorClient: HttpClient = KtorClient.client
): HomeFeedRepository {

    override suspend fun getStudentTable(cardID: String): ApiResponse {
        return try {
            val response = ktorClient.get(
                BASE_URL + PARENTS + CARD + "${cardID}/" + GROUPS
                ) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken) // Replace with the actual token value
            }

            Log.e("table", response.request.toString())
            Log.e("table", response.bodyAsText())
            Log.e("table", BASE_URL + PARENTS + CARD + "${cardID}/" + GROUPS)

            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                401 -> {
                    // Handle unauthorized error (TODO: Implement re-login logic)
                    ApiResponse.Unauthorized
                }
                403 -> {
                    // Handle forbidden error (TODO: Implement appropriate message)
                    ApiResponse.Refused
                }
                else -> {
                    ApiResponse.Failed(UNKNOWN)
                }
            }
        } catch (e: Exception) {
            // Handle other exceptions
            ApiResponse.Error
        }
    }

    override suspend fun getAttendanceRecords(dayOfWeek: String, cardId: String): ApiResponse {
        Log.e("Record", "calling data")
        return try {
            val response = ktorClient.get(
                BASE_URL + ATTENDANCE + STUDENT + CARDS + "${cardId}/"
            ) {
                contentType(ContentType.Text.Plain)
                parameter("day", dayOfWeek)
                header("auth-token", authToken) // Replace with the actual token value
            }

            Log.e("Record", response.status.value.toString())
            when (response.status.value) {
                in 200..300 -> {

                    ApiResponse.Success(
                        data = response.bodyAsText()
//                        convertJsonToAttendanceRecord(response.bodyAsText())
                    )
                }
                401 -> {
                    // Handle unauthorized error (TODO: Implement re-login logic)
                    ApiResponse.Unauthorized
                }
                403 -> {
                    // Handle forbidden error (TODO: Implement appropriate message)
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

    override suspend fun getStudents(): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + STUDENTS) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("ME", response.bodyAsText())
            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                401 -> {
                    // Handle unauthorized error (TODO: Implement re-login logic)
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
            // Handle other exceptions
            ApiResponse.Error
        }
    }

    data class ParentKey(val parentKey: String)
    @OptIn(InternalAPI::class)
    override suspend fun addStudent(parentKey: String): ApiResponse {
        val jsonBody = gson.toJson(ParentKey(parentKey = parentKey))
        return try {
            val response = ktorClient.post(BASE_URL + PARENTS + STUDENTS) {
                contentType(ContentType.Text.Plain)
                body = TextContent(jsonBody, ContentType.Application.Json)
                header("auth-token", authToken)
            }

            Log.e("Auth", response.bodyAsText())
            Log.e("Auth", authToken)
            Log.e("Auth", jsonBody)
            Log.e("Auth", response.status.value.toString())

            when (response.status.value) {
                in 200..300 -> {
                    ApiResponse.Success(
                        data = response.bodyAsText()
                    )
                }
                401 -> {
                    ApiResponse.Unauthorized
                    // TODO("Re-Login")
                }
                403 -> {
                    // TODO("say refused")
                    ApiResponse.Refused
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