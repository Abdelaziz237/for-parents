package com.sanadedu.parent.feed.domain.model

import com.sanadedu.parent.feed.domain.dto.CoursesCountDTO
import com.sanadedu.parent.feed.domain.dto.StudentDTO

data class Student(
    val studentInfo: StudentDTO,
    val coursesCount: CoursesCountDTO
)