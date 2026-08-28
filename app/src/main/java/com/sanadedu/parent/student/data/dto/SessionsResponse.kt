package com.sanadedu.parent.student.data.dto

data class SessionsResponse(
    val status: String,
    val data: List<SessionDTO>,
    val metadata: Metadata
)

data class SessionDTO(
    val _id: String,
    val createdBy: CreatedByDTO,
    val topics: List<String>,
    val isExamAdded: Boolean,
    val maxStreamViews: Int,
    val fullAccessPrice: Int,
    val tutorCourse: String,
    val name: String,
    val type: String,
    val sessionNumber: Int,
    val createdAt: String,
    val updatedAt: String,
    val isAttended: String,
    val groupData: GroupInfoDTO?,
    val attendTime: String?,
    val sessionFees: Double?
)


data class GroupInfoDTO(
    val _id: String,
    val groupNumber: Int,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String
)

data class Metadata(
    val totalDocs: Int,
    val totalPages: Int,
    val page: Int,
    val nextPage: Any?, // Replace Any? with appropriate type if known
    val prevPage: Any? // Replace Any? with appropriate type if known
)
