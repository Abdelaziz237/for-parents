package com.sanadedu.parent.feed.domain.dto.response

import com.sanadedu.parent.feed.domain.dto.MetadataDTO

data class AttendanceResponse(
    val status: String,
    val data: List<AttendanceEntryDTO>,
    val metadata: MetadataDTO
)

data class AttendanceEntryDTO(
    val _id: String,
    val center: String,
    val tutorCourse: String,
    val session: SessionDTO,
    val group: String?,
    val card: String,
    val isAttended: Boolean,
    val sessionAccess: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val DD: String,
    val group_data: GroupDTO
)

data class SessionDTO(
    val _id: String,
    val sessionNumber: Int,
    val name: String,
    val type: String
)

data class GroupDTO(
    val centerCourse: String,
    val center: CenterDTO,
    val tutorCourse: TutorCourseDTO,
    val groupNumber: Int,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String
)