package com.sanadedu.parent.auth.domain.dto

data class UserDTO(
    val _id: String,
    val fullname: String,
    val email: String,
    val code: String,
    val students: List<StudentID>,
    val emailCampaigns: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val address: String,
    val birthDate: String,
    val city: String,
    val gender: String,
    val governorate: String,
    val profileImage: String,
    val phoneNumber: String
)