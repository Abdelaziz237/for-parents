package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.student.data.dto.CoursesResponse
import com.sanadedu.parent.student.domain.repository.StudentRepository

class GetCourses(
    private val repository: StudentRepository
) {
    private val validator: DataValidator<CoursesResponse> = DataValidator(
        dtoClass = CoursesResponse::class.java
    )

    suspend operator fun invoke(cardId: String): ValidationStatus<CoursesResponse> {
        val response = repository.getCourses(cardId = cardId)
        return validator.validateData(response)
    }
}
