package com.sanadedu.parent.student.domain.repository

import com.sanadedu.parent.core.domain.model.ApiResponse

interface StudentRepository {
    suspend fun getCourses(cardId: String): ApiResponse
    suspend fun getCenters(cardId: String): ApiResponse
    suspend fun getCourseSessions(cardId: String, tutorCourseId: String): ApiResponse
    suspend fun getCourseExams(cardId: String, tutorCourseId: String): ApiResponse
    suspend fun getNotifications(cardId: String): ApiResponse
}