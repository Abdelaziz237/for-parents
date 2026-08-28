package com.sanadedu.parent.feed.domain.util

import com.sanadedu.parent.feed.domain.dto.CoursesCountDTO
import com.sanadedu.parent.feed.domain.dto.StudentDTO
import com.sanadedu.parent.feed.domain.model.Student

fun CoursesCountDTO.attachToStudent(students: List<StudentDTO>): Student? {
    var student: Student? = null
    students.forEach {
        if (this._id == it.card) {
            student = Student(studentInfo = it, coursesCount = this)
        }
    }
    return student
}