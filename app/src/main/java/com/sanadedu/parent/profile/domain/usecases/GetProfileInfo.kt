package com.sanadedu.parent.profile.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.profile.data.dto.ProfileResponse
import com.sanadedu.parent.profile.domain.repository.ProfileRepository

class GetProfileInfo (
    private val repository: ProfileRepository
) {
    private val validator: DataValidator<ProfileResponse> = DataValidator(
        dtoClass = ProfileResponse::class.java
    )

    suspend operator fun invoke(): ValidationStatus<ProfileResponse> {
        val response = repository.getProfileInfo()
        return validator.validateData(response)
    }
}