package com.sanadedu.parent.feed.domain.dto.response

import com.sanadedu.parent.feed.domain.dto.MetadataDTO

data class ScheduleResponse(
    val status: String,
    val data: List<ScheduleDTO>,
    val metadata: MetadataDTO
)

data class ScheduleDTO(
    val _id: String,
    val centerCourse: String,
    val center: CenterDTO,
    val tutorCourse: TutorCourseDTO,
    val groupNumber: Int,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String
)