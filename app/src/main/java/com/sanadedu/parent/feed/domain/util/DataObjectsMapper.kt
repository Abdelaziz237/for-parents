package com.sanadedu.parent.feed.domain.util

import com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils.DefaultDateUtil
import com.sanadedu.parent.feed.domain.model.AttendanceSession
import com.sanadedu.parent.feed.presentation.home.items.SessionItem

fun  List<AttendanceSession>.toSessionItems(): List<SessionItem> {
    return this.map { record ->
        SessionItem(
            courseName = record.courseName,
            centerName =  record.centerName,
            groupNumber = record.groupNumber,
            tutorName = record.tutorName,
            isAttended = record.isAttended,
            isPending = record.isPending,
            createdAt = record.createdAt,
            dayOfMonth = -1,
            dayOfWeek = record.dayOfWeek.toInt(),
            hour = DefaultDateUtil.formatHourFromTimestamp(record.startAt)
        )
    }
}