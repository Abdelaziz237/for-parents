package com.sanadedu.parent.feed.domain.model

data class AttendanceSessions(
    val sessions: List<AttendanceSession>
)

data class AttendanceSession(
    val id: String,
    val courseName: String,
    val sessionName: String,
    val sessionNumber: String,
    val groupNumber: String,
    val centerName: String,
    val tutorName: String,
    val isAttended: Boolean,
    val isPending: Boolean,
    val dayOfWeek: String,
    val tags: List<String>,
    val createdAt: String,
    val startAt: String,
    val endAt: String
)