package com.sanadedu.parent.student.domain.util

import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem


object CourseDataObject {

    private lateinit var selectedCourse: CourseItem

    fun setCourse(course: CourseItem) {
        selectedCourse = course
    }

    fun getCourse(): CourseItem {
        if (!::selectedCourse.isInitialized) {
            throw IllegalStateException("CourseInfo is not initialized. Call setCourse() first.")
        }
        return selectedCourse
    }
}