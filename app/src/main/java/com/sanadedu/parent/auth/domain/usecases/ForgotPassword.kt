package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.data.body_dtos.ResetPasswordLinkBodyDTO
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator


class ForgotPassword(
    private val repository: AuthRepository
) {
    private val validator: DataValidator<String> = DataValidator(String::class.java)

    suspend operator fun invoke(
        email:String,
    ): ValidationStatus<String> {
        val body = ResetPasswordLinkBodyDTO(
            emailOrCode = email,
        )
        val response = repository.resetPassword(body)
        return validator.validateData(response, needsDecoding = false)
    }
}