package com.sanadedu.parent.auth.domain.dto

data class SignInResponseDTO(
    val status: String,
    val token: String,
    val data: UserDTO
)
