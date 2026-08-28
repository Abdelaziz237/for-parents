package com.sanadedu.parent.auth.presentation.sign_in.info

sealed class LoginPageEvent {
    data class Login(val email: String, val password: String): LoginPageEvent()
    data class ShowErrorMessage(val message: Int): LoginPageEvent()
    object StartLoading: LoginPageEvent()
    object StopLoading: LoginPageEvent()
}