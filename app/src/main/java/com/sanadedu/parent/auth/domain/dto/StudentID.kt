package com.sanadedu.parent.auth.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class StudentID(
    val _id: String,
    val card: String
)
