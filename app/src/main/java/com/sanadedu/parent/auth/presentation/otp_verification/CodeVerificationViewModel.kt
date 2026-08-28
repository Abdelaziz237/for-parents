package com.sanadedu.parent.auth.presentation.otp_verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.sanadedu.parent.auth.presentation.AuthEvent
import com.sanadedu.parent.auth.presentation.AuthViewModel

class CodeVerificationViewModel: AuthViewModel() {

    private val _state = mutableStateOf(false)
    val state: State<Boolean> = _state
    fun onEvent(event: OtpVerificationPageEvent, callback: (Boolean) -> Unit) {
        when(event) {
            is OtpVerificationPageEvent.StartLoading -> {
                _state.value = true
            }
            is OtpVerificationPageEvent.VerifyOtp -> {
                super.onEvent(
                    event = AuthEvent.VerifyOtp(
                        email = event.email,
                        otp = event.otp
                    ),
                    callback = { isVerified ->
                        if (isVerified) callback(true)
                        onEvent(OtpVerificationPageEvent.StopLoading, callback = {})
                    }
                )
            }
            is OtpVerificationPageEvent.ReSendOtp -> {
                super.onEvent(
                    event = AuthEvent.ReSendOtp(
                        email = event.email
                    ),
                    callback = { isSent ->
                        if (isSent) callback(true)
                        onEvent(OtpVerificationPageEvent.StopLoading, callback = {})
                    }
                )
            }

            is OtpVerificationPageEvent.StopLoading -> {
                _state.value = false
            }
        }
    }
}