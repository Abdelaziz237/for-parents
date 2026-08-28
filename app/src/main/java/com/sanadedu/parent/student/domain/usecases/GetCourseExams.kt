package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.student.data.dto.ExamsResponse
import com.sanadedu.parent.student.data.dto.SessionsResponse
import com.sanadedu.parent.student.domain.repository.StudentRepository

class GetCourseExams (
    private val repository: StudentRepository
) {
    private val validator: DataValidator<ExamsResponse> = DataValidator(
        dtoClass = ExamsResponse::class.java
    )

    suspend operator fun invoke(cardId: String, tutorCourseId: String): ValidationStatus<ExamsResponse> {
        val response = repository.getCourseExams(cardId = cardId, tutorCourseId = tutorCourseId)
        return validator.validateData(response)
    }
}
