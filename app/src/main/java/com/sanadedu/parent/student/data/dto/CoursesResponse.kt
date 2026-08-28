package com.sanadedu.parent.student.data.dto

import com.sanadedu.parent.feed.domain.dto.MetadataDTO

data class CoursesResponse(
    val status: String,
    val data: List<CourseInfoDTO>,
    val metadata: MetadataDTO
)

data class CourseInfoDTO(
    val _id: String,
    val tutorCourse: TutorCourseInfoDTO,
    val paymentType: String,
    val credits: Int,
    val discountPercent: Int,
    val alerts: Int,
    val sessions_count: Int,
    val centerName: String,
    val centerCode: String
)

data class TutorCourseInfoDTO(
    val _id: String,
    val tutor: TutorDTO,
    val courseData: CourseDataDTO
)

data class TutorDTO(
    val _id: String,
    val fullname: String,
    val code: String,
    val profileImage: String
)

data class CourseDataDTO(
    val name: String,
    val code: String,
    val image: String
)