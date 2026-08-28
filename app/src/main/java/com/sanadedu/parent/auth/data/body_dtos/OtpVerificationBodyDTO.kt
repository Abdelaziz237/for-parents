package com.sanadedu.parent.auth.data.body_dtos

data class OtpVerificationBodyDTO(
    val email: String,
    val otp: String
)

