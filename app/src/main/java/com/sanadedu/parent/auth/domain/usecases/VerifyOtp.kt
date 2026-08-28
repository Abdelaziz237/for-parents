package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.data.body_dtos.OtpVerificationBodyDTO
import com.sanadedu.parent.auth.domain.repository.AuthRepository
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator

class VerifyOtp(
    private val repository: AuthRepository
) {
    private val validator: DataValidator<String> = DataValidator(String::class.java)

    suspend operator fun invoke(
        email:String,
        otp: String,
    ): ValidationStatus<String> {
        val body = OtpVerificationBodyDTO(
            email = email,
            otp = otp
        )
        val response = repository.verifyOtp(body)
        return validator.validateData(response, needsDecoding = false)
    }
}