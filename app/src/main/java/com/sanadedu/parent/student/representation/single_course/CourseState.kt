package com.sanadedu.parent.student.representation.single_course

import com.sanadedu.parent.student.representation.single_course.data.ExamItemForCourse
import com.sanadedu.parent.student.representation.single_course.data.SessionItemForCourse
import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem

data class CourseState(
    val isLoading: Boolean = false,
    val course: CourseItem = CourseItem(),
    val avgExams: Float = 0f,
    val avgAttendance: Float = 0f,
    val sessions: List<SessionItemForCourse> = emptyList(),
    val exams: List<ExamItemForCourse> = emptyList(),
)
