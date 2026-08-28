package com.sanadedu.parent.auth.presentation.otp_verification


sealed class OtpVerificationPageEvent {
    object StartLoading: OtpVerificationPageEvent()
    data class VerifyOtp(val email: String, val otp: String): OtpVerificationPageEvent()
    data class ReSendOtp(val email: String): OtpVerificationPageEvent()
    object StopLoading: OtpVerificationPageEvent()
}