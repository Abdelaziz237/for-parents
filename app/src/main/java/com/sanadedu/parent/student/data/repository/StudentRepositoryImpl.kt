package com.sanadedu.parent.student.data.repository

import android.util.Log
import com.sanadedu.parent.core.data.NetworkFailedCodes.UNKNOWN
import com.sanadedu.parent.core.data.NetworkRoutes.ANNOUNCEMENTS
import com.sanadedu.parent.core.data.NetworkRoutes.BASE_URL
import com.sanadedu.parent.core.data.NetworkRoutes.CARDS
import com.sanadedu.parent.core.data.NetworkRoutes.CARD_COURSES
import com.sanadedu.parent.core.data.NetworkRoutes.CENTERS
import com.sanadedu.parent.core.data.NetworkRoutes.EXAMS
import com.sanadedu.parent.core.data.NetworkRoutes.PARENTS
import com.sanadedu.parent.core.data.NetworkRoutes.TUTOR_COURSES
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import com.sanadedu.parent.student.domain.repository.StudentRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class StudentRepositoryImpl(
    private val authToken: String,
    private val ktorClient: HttpClient = KtorClient.client
): StudentRepository {
    override suspend fun getCourses(cardId: String): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + CARD_COURSES + cardId) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("getCourses", response.bodyAsText())
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

    override suspend fun getCenters(cardId: String): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + CARDS + cardId + CENTERS) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("getCenters", response.bodyAsText())
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

    override suspend fun getCourseSessions(cardId: String, tutorCourseId: String): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + CARDS + cardId + TUTOR_COURSES + tutorCourseId) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("getCenters", response.bodyAsText())
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

    override suspend fun getCourseExams(cardId: String, tutorCourseId: String): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + CARDS + cardId + TUTOR_COURSES + tutorCourseId + EXAMS) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("getExams", response.bodyAsText())
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

    override suspend fun getNotifications(cardId: String): ApiResponse {
        return try {
            val response = ktorClient.get(BASE_URL + PARENTS + CARDS + cardId + ANNOUNCEMENTS) {
                contentType(ContentType.Text.Plain)
                header("auth-token", authToken)
            }

            Log.e("getExams", response.bodyAsText())
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
}