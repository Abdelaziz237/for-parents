package com.sanadedu.parent.student.representation.single_course

sealed class SingleCourseEvents {
    data object StartLoading: SingleCourseEvents()
    data object GetCourseData: SingleCourseEvents()
    data object StopLoading: SingleCourseEvents()
}