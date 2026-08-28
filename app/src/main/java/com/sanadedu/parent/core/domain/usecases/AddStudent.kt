package com.sanadedu.parent.core.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.feed.domain.dto.AddedStudentDTO
import com.sanadedu.parent.feed.domain.repository.HomeFeedRepository

class AddStudent (
    private val repository: HomeFeedRepository
) {
    private val validator: DataValidator<AddedStudentDTO> = DataValidator(
        dtoClass = AddedStudentDTO::class.java
    )

    suspend operator fun invoke(parentKey: String): ValidationStatus<AddedStudentDTO> {
        val response = repository.addStudent(parentKey)
        return validator.validateData(response)
    }
}