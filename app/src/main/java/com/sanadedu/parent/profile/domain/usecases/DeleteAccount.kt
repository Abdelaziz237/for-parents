package com.sanadedu.parent.profile.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.profile.domain.repository.ProfileRepository

class DeleteAccount (
    private val repository: ProfileRepository
) {
    private val validator: DataValidator<String> = DataValidator(
        dtoClass = String::class.java
    )

    suspend operator fun invoke(): ValidationStatus<String> {
        val response = repository.getProfileInfo()
        return validator.validateData(response, needsDecoding = false)
    }
}