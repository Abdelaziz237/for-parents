package com.sanadedu.parent.auth.presentation

sealed class AuthEvent {
    data class VerifyOtp(val email: String, val otp: String): AuthEvent()
    data class ReSendOtp(val email: String) : AuthEvent()
}