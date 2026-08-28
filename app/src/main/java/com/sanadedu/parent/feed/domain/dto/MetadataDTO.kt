package com.sanadedu.parent.feed.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetadataDTO(
    val totalDocs: Int,
    val totalPages: Int,
    val page: Int,
    val nextPage: Int?, // Updated to String? to handle null value
    val prevPage: Int?  // Updated to String? to handle null value
)
