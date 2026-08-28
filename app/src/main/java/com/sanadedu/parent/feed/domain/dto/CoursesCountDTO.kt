package com.sanadedu.parent.feed.domain.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoursesCountDTO(
    val _id: String,
    val count: Double
)
