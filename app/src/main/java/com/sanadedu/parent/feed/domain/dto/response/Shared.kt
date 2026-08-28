package com.sanadedu.parent.feed.domain.dto.response

data class CenterDTO(
    val _id: String,
    val name: String,
    val code: String
)

data class TutorCourseDTO(
    val _id: String,
    val tutor: TutorDTO,
    val courseData: CourseDTO,
    val term: String,
    val year: String? = null
)

data class TutorDTO(
    val _id: String,
    val fullname: String,
    val code: String,
    val profileImage: String
)

data class CourseDTO(
    val _id: String,
    val grade: GradeDTO,
    val name: String,
    val code: String,
    val image: String
)

data class GradeDTO(
    val _id: String,
    val nameEn: String,
    val nameAr: String
)