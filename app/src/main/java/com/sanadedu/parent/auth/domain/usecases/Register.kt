package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.domain.dto.SignInResponseDTO
import com.sanadedu.parent.auth.domain.dto.SignUpResponseDTO
import com.sanadedu.parent.auth.domain.model.User
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.auth.domain.util.decodeSignUpResponse
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator

class Register(
    private val repository: AuthRepository
) {
    private val validator: DataValidator<SignUpResponseDTO> = DataValidator(
        dtoClass = SignUpResponseDTO::class.java
    )

    suspend operator fun invoke(user: User.UserForRegister): ValidationStatus<SignUpResponseDTO> {
        val response = repository.register(user)
        return validator.validateData(response)
    }
}