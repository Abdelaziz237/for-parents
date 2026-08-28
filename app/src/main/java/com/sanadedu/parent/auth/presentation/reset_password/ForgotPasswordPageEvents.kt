package com.sanadedu.parent.auth.presentation.reset_password

sealed class ForgotPasswordPageEvents {
    object StartLoading: ForgotPasswordPageEvents()
    data class SendResetPasswordLink(val email: String): ForgotPasswordPageEvents()
    object StopLoading: ForgotPasswordPageEvents()
}