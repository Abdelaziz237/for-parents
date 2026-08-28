package com.sanadedu.parent.feed.domain.repository

import com.sanadedu.parent.core.domain.model.ApiResponse

interface HomeFeedRepository {
    suspend fun getStudentTable(cardID: String): ApiResponse

    suspend fun getAttendanceRecords(dayOfWeek: String, cardId: String): ApiResponse

    suspend fun getStudents(): ApiResponse // token -> header

    suspend fun addStudent(parentKey: String): ApiResponse // token -> header,  key -> body

}