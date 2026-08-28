package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.student.domain.repository.StudentRepository

data class StudentUseCases(
    val getCourses: GetCourses,
    val getCenters: GetCenters,
    val getCourseSessions: GetCourseSessions,
    val getCourseExams: GetCourseExams,
    val getNotifications: GetNotifications
) {
    constructor(repository: StudentRepository) : this(
        getCourses = GetCourses(repository),
        getCenters = GetCenters(repository),
        getCourseSessions = GetCourseSessions(repository),
        getCourseExams = GetCourseExams(repository),
        getNotifications = GetNotifications(repository)
    )
}
