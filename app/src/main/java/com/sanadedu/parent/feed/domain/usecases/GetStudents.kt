package com.sanadedu.parent.feed.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.feed.domain.dto.StudentsDTO
import com.sanadedu.parent.feed.domain.repository.HomeFeedRepository

class GetStudents (
    private val repository: HomeFeedRepository
) {
    private val validator: DataValidator<StudentsDTO> = DataValidator(
        dtoClass = StudentsDTO::class.java
    )

    suspend operator fun invoke(): ValidationStatus<StudentsDTO> {
        val response = repository.getStudents()
        return validator.validateData(response)
    }
}