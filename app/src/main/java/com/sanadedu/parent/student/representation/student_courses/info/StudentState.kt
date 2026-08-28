package com.sanadedu.parent.student.representation.student_courses.info

import com.sanadedu.parent.student.representation.student_courses.info.data.CenterItem
import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem

data class StudentState(
    val isLoading: Boolean = true,
    val courses: List<CourseItem> = emptyList(),
    val centers: List<CenterItem> = emptyList(),
)
