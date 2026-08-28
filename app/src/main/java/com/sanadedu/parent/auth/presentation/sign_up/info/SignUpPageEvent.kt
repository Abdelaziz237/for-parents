package com.sanadedu.parent.auth.presentation.sign_up.info

import com.sanadedu.parent.auth.presentation.sign_in.info.LoginPageEvent

sealed class SignUpPageEvent {
    data class VerifyUser(
        val fullName: String,
        val email:String,
        val password: String,
        val emailCampaigns: Boolean
    ): SignUpPageEvent()
    object StartLoading: SignUpPageEvent()
    object StopLoading: SignUpPageEvent()
}