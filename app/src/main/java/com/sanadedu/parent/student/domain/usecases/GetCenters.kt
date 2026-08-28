package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.student.data.dto.CentersResponse
import com.sanadedu.parent.student.domain.repository.StudentRepository

class GetCenters(
    private val repository: StudentRepository
) {
    private val validator: DataValidator<CentersResponse> = DataValidator(
        dtoClass = CentersResponse::class.java
    )

    suspend operator fun invoke(cardId: String): ValidationStatus<CentersResponse> {
        val response = repository.getCenters(cardId = cardId)
        return validator.validateData(response)
    }
}