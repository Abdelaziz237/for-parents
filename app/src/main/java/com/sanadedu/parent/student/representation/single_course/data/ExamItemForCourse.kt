package com.sanadedu.parent.student.representation.single_course.data

data class ExamItemForCourse(
    val examName: String,
    val tutorName: String,
    val grade: Float,
    val centerName: String,
    val tags: List<String>,
    val examNumber: String
)
