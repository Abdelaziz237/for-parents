package com.sanadedu.parent.auth.domain.model

sealed class User {
    data class UserForVerify(
        val fullname: String,
        val email:String,
        val password: String,
        val confirmPassword: String,
        val emailCampaigns: Boolean
    )
    data class UserForRegister(
        val email: String,
        val governorate: String,
        val city: String,
        val address: String,
        val phoneNumber: String,
        val gender: String,
        val birthDate: String,
        val profileImage: String,
    ) {
        constructor(email: String) : this(
            email = email,
            governorate = "",
            city = "",
            address = "",
            phoneNumber = "",
            gender = "n",
            birthDate = "",
            profileImage = "",
        )
    }
}