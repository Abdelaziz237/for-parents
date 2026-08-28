package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.info

import java.io.File

sealed class RegisterFormEvent{
    data object StartLoading: RegisterFormEvent()

    data class SubmitAddress(
        val government: String,
        val city: String,
        val address: String,
    ): RegisterFormEvent()

    data class SubmitPhoneNumber(
        val fullNumber: String
    ): RegisterFormEvent()

    data class SubmitBirthdateAndGender(
        val birthdate: String,
        val gender: String
    ): RegisterFormEvent()

    data class SubmitImage(
        val profileImagePath: String,
    ): RegisterFormEvent()

    data class UploadImage(val email : String, val imageFile: File, val mimeType: String): RegisterFormEvent()

    data object SubmitForm: RegisterFormEvent()

    data object StopLoading: RegisterFormEvent()
}