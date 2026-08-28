package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.data.body_dtos.SignInBodyDTO
import com.sanadedu.parent.auth.domain.model.User
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.core.domain.model.ConversionResult
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator

class VerifyEmail(
    private val repository: AuthRepository
) {

    private val validator: DataValidator<String> = DataValidator(
        dtoClass = String::class.java
    )

    suspend operator fun invoke(
        fullName: String,
        email:String,
        password: String,
        emailCampaigns: Boolean
    ): ValidationStatus<String> {
        val body = User.UserForVerify(
            fullname = fullName,
            email = email,
            password = password,
            confirmPassword = password,
            emailCampaigns = emailCampaigns
        )
        val response = repository.verifyEmail(body)
        return validator.validateData(response = response, needsDecoding = false)
    }
}