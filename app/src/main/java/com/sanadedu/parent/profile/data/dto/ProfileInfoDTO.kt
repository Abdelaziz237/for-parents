package com.sanadedu.parent.profile.data.dto

data class ProfileResponse(
    val status: String,
    val data: ProfileInfoDTO
)

data class ProfileInfoDTO(
    val fullname: String,
    val email: String,
    val code: String,
    val phoneNumber: String,
    val studentsNumber: Int
)