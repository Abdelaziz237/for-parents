package com.sanadedu.parent.feed.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class GradeDTO (
    val _id: String,
    val nameEn: String,
    val nameAr: String
)
