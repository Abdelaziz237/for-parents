package com.sanadedu.parent.student.domain.util

import com.sanadedu.parent.student.representation.student_courses.info.data.StudentInfo

object StudentDataObject {

    private lateinit var selectedStudent: StudentInfo

    fun setStudent(studentInfo: StudentInfo) {
        selectedStudent = studentInfo
    }

    fun getStudent(): StudentInfo {
        if (!::selectedStudent.isInitialized) {
            throw IllegalStateException("StudentInfo is not initialized. Call setStudent() first.")
        }
        return selectedStudent
    }
}