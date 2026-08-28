package com.sanadedu.parent.auth.data.body_dtos

import kotlinx.serialization.Serializable

@Serializable
data class SignInBodyDTO(
    val emailOrCode: String,
    val password: String
)