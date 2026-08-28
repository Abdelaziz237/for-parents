package com.sanadedu.parent.student.representation.student_courses.info

sealed class StudentScreenEvents {
    data object StartLoading : StudentScreenEvents()
    data object GetCenters : StudentScreenEvents()
    data object GetCourses : StudentScreenEvents()
    data class FilterCoursesByCenter(val centerCode: String) : StudentScreenEvents()
    data object StopLoading : StudentScreenEvents()
}