package com.sanadedu.parent.auth.domain.dto

data class SignUpResponseDTO(
    val status: String,
    val token: String,
    val data: UserDTO
)
