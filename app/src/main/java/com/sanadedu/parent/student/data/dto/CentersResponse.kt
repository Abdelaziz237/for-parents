package com.sanadedu.parent.student.data.dto

import com.sanadedu.parent.feed.domain.dto.MetadataDTO

data class CentersResponse(
    val status: String,
    val data: List<CenterDTO>,
    val metadata: MetadataDTO
)

data class CenterDTO(
    val _id: String,
    val name: String,
    val code: String
)
