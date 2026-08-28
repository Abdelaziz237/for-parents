package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.student.data.dto.SessionsResponse
import com.sanadedu.parent.student.domain.repository.StudentRepository

class GetCourseSessions (
    private val repository: StudentRepository
) {
    private val validator: DataValidator<SessionsResponse> = DataValidator(
        dtoClass = SessionsResponse::class.java
    )

    suspend operator fun invoke(cardId: String, tutorCourseId: String): ValidationStatus<SessionsResponse> {
        val response = repository.getCourseSessions(cardId = cardId, tutorCourseId = tutorCourseId)
        return validator.validateData(response)
    }
}
