package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.data.body_dtos.SignInBodyDTO
import com.sanadedu.parent.auth.domain.dto.SignInResponseDTO
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.auth.domain.util.decodeSignInResponse
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator

class SignIn(
    private val repository: AuthRepository
) {
    private val validator: DataValidator<SignInResponseDTO> = DataValidator(
        dtoClass = SignInResponseDTO::class.java
    )

    suspend operator fun invoke(emailOrCode: String, password: String): ValidationStatus<SignInResponseDTO> {
        val body = SignInBodyDTO(emailOrCode, password)
        val response = repository.signIn(body)
        return validator.validateData(response)
    }
}