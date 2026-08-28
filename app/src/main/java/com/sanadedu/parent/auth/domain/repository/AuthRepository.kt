package com.sanadedu.parent.auth.domain.repository

import com.sanadedu.parent.auth.data.body_dtos.OtpVerificationBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.ResendOtpBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.ResetPasswordLinkBodyDTO
import com.sanadedu.parent.auth.data.body_dtos.SignInBodyDTO
import com.sanadedu.parent.auth.domain.model.User
import com.sanadedu.parent.core.domain.model.ApiResponse

interface AuthRepository {
    suspend fun signIn(bodyValue: SignInBodyDTO): ApiResponse
    suspend fun verifyEmail(bodyValue: User.UserForVerify): ApiResponse
    suspend fun verifyOtp(bodyValue: OtpVerificationBodyDTO): ApiResponse
    suspend fun resendOtp(bodyValue: ResendOtpBodyDTO): ApiResponse
    suspend fun register(bodyValue: User.UserForRegister): ApiResponse
    suspend fun resetPassword(bodyValue: ResetPasswordLinkBodyDTO): ApiResponse
}