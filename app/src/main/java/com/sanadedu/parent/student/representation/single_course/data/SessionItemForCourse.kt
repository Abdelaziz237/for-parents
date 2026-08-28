package com.sanadedu.parent.student.representation.single_course.data

data class SessionItemForCourse(
    val sessionName: String,
    val courseName: String,
    val isAttended: Boolean,
    val isPending: Boolean,
    val tutorName: String,
    val sessionFees: String,
    val attendedDay: String,
    val attendedHour: String,
    val groupNumber: String,
)
