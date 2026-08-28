package com.sanadedu.parent.feed.domain.usecases

import android.util.Log
import com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils.DefaultDateUtil
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.core.presentation.getDayString
import com.sanadedu.parent.feed.domain.dto.response.AttendanceResponse
import com.sanadedu.parent.feed.domain.dto.response.ScheduleResponse
import com.sanadedu.parent.feed.domain.model.AttendanceSession
import com.sanadedu.parent.feed.domain.model.AttendanceSessions
import com.sanadedu.parent.feed.domain.repository.HomeFeedRepository

class GetAttendanceForWeek(
    private val repository: HomeFeedRepository
) {
    private val scheduleValidator: DataValidator<ScheduleResponse> = DataValidator(
        ScheduleResponse::class.java
    )

    private val attendanceValidator: DataValidator<AttendanceResponse> = DataValidator(
        AttendanceResponse::class.java
    )

    suspend operator fun invoke(cardId: String, dayOfWeek: String): ValidationStatus<AttendanceSessions> {
        val groupsResponse = repository.getStudentTable(cardID = cardId)
        val groupsResponseValidated = scheduleValidator.validateData(groupsResponse)

        val attendanceResponse = repository.getAttendanceRecords(dayOfWeek = dayOfWeek, cardId = cardId)
        val attendanceResponseValidated = attendanceValidator.validateData(attendanceResponse)

        return combineGroupsWithAttendance(groupsResponseValidated, attendanceResponseValidated, dayOfWeek)
    }

    private fun combineGroupsWithAttendance(
        groupsValidated: ValidationStatus<ScheduleResponse>,
        attendanceValidated: ValidationStatus<AttendanceResponse>,
        dayOfWeek: String
    ): ValidationStatus<AttendanceSessions> {
        val sessions = mutableListOf<AttendanceSession>()

        return when(groupsValidated) {
            is ValidationStatus.Valid -> {
                when (attendanceValidated) {
                    is ValidationStatus.Valid -> {
                        val groups = groupsValidated.data.data
                        val records = attendanceValidated.data.data

                        groups.forEach { group ->
                            var isFound = false
                            records.forEach { record ->
                                if (record.group != null && group._id == record.group) {
                                    isFound = true
                                    sessions.add(
                                        AttendanceSession(
                                            id = record.session._id,
                                            courseName = record.group_data.tutorCourse.courseData.name,
                                            sessionName = record.session.name,
                                            sessionNumber = record.session.sessionNumber.toString(),
                                            groupNumber = group.groupNumber.toString(),
                                            centerName = group.center.name,
                                            tutorName = group.tutorCourse.tutor.fullname,
                                            isAttended = true,
                                            isPending = false,
                                            dayOfWeek = group.dayOfWeek.toString(),
                                            tags = listOf(group.tutorCourse.courseData.name, group.tutorCourse.courseData.grade.nameEn),
                                            createdAt = record.createdAt,
                                            startAt = group.startTime,
                                            endAt = group.endTime
                                        )
                                    )
                                }
                            }

                            if (!isFound) {
                                Log.e("groups", group.toString())
                                sessions.add(
                                    AttendanceSession(
                                        id = "",
                                        courseName = group.tutorCourse.courseData.name,
                                        sessionName = "",
                                        sessionNumber = "",
                                        groupNumber = group.groupNumber.toString(),
                                        centerName = group.center.name,
                                        tutorName = group.tutorCourse.tutor.fullname,
                                        tags = listOf(getDayString(group.dayOfWeek), DefaultDateUtil.formatHourFromTimestamp(group.startTime), group.tutorCourse.courseData.grade.nameEn),
                                        isAttended = false,
                                        isPending = (group.dayOfWeek >= dayOfWeek.toInt()),
                                        dayOfWeek = group.dayOfWeek.toString(),
                                        createdAt = group.endTime,
                                        startAt = group.startTime,
                                        endAt = group.endTime
                                    )
                                )
                            }
                        }

                        records.forEach { record ->
                            var notFound = true
                            groups.forEach { group ->
                                if (group._id == record.group) notFound = false
                            }

                            if (notFound && record.group != null) {
                                sessions.add(
                                    AttendanceSession(
                                        id = record.session._id,
                                        courseName = record.group_data.tutorCourse.courseData.name,
                                        sessionName = record.session.name,
                                        sessionNumber = record.session.sessionNumber.toString(),
                                        groupNumber = record.group_data.groupNumber.toString(),
                                        centerName = record.group_data.center.name,
                                        tutorName = record.group_data.tutorCourse.tutor.fullname,
                                        tags = listOf(record.group_data.tutorCourse.courseData.name, record.group_data.tutorCourse.courseData.grade.nameEn),
                                        isAttended = true,
                                        isPending = (record.group_data.dayOfWeek >= dayOfWeek.toInt()),
                                        dayOfWeek = record.group_data.dayOfWeek.toString(),
                                        createdAt = record.createdAt,
                                        startAt = record.group_data.startTime,
                                        endAt = record.group_data.endTime
                                    )
                                )
                            }
                        }
                        ValidationStatus.Valid(data = AttendanceSessions(sessions = sessions))
                    }

                    is ValidationStatus.NotValid -> {
                        ValidationStatus.NotValid(attendanceValidated.cause)
                    }
                }
            }

            is ValidationStatus.NotValid -> {
                ValidationStatus.NotValid(groupsValidated.cause)
            }
        }
    }
}

//    private val recordsValidator: DataValidator<AttendanceRecordDTO> = DataValidator(
//        dtoClass = AttendanceRecordDTO::class.java
//    )
//
//    private val groupsValidator: DataValidator<StudentTableDTO> = DataValidator(
//        dtoClass = StudentTableDTO::class.java
//    )
//
//    suspend operator fun invoke(dayOfWeek: String, studentID: String, cardID: String): ValidationStatus<List<SessionItem>> {
//        when (val records = getAllRecords(dayOfWeek, studentID)) {
//
//            is ValidationStatus.Valid -> {
//
//                when (val studentTable = getStudentTable(cardID)) {
//                    is ValidationStatus.Valid -> {
//                        combineGroupsWithAttendance(studentTable.data.data, records.data.data, dayOfWeek)
//                    }
//                    is ValidationStatus.NotValid -> {
//
//                    }
//                }
//            }
//
//            is ValidationStatus.NotValid -> {
//
//            }
//        }
//
//
//        return if (allRecords.isEmpty()) {
//            prepareTable(studentTable)
//        } else if (studentTable.data.isEmpty().not()) compareRecords(allRecords, studentTable, dayOfWeek)
//        else emptyList()
//    }
//
//    private fun combineGroupsWithAttendance(
//        groups: List<GroupsDTO>,
//        attendanceRecords: List<RecordDTO>,
//        dayOfWeek: String
//    ): ValidationStatus<List<SessionItem>> {
//        return ValidationStatus.Valid(
//
//            if (attendanceRecords.isEmpty()) prepareTable(groups)
//            else if (groups.isEmpty().not()) compareRecords(records = attendanceRecords, table = groups, dayOfWeek = dayOfWeek)
//            else emptyList()
//
//        )
//    }
//
//    private fun prepareTable(studentGroups: List<GroupsDTO>): List<SessionItem> {
//        val sessions = mutableListOf<SessionItem>()
//
//        studentGroups.forEach { group ->
//            group.allGroupData.forEach { data ->
//                sessions.add(
//                    SessionItem(
//                        courseName = data.tutorCourse.courseData.name,
//                        centerName = data.center.name,
//                        groupNumber = data.groupNumber.toString(),
//                        tutorName = data.tutorCourse.tutor.fullname,
//                        dayOfWeek = data.dayOfWeek,
//                        isAttended = false,
//                        isPending = (data.dayOfWeek >= Integer.valueOf(getCurrentDay())),
//                        createdAt = data.startTime
//                    )
//                )
//            }
//        }
//        return sessions
//    }
//
//    private suspend fun getAllRecords(dayOfWeek: String, studentID: String): ValidationStatus<AttendanceRecordDTO> {
//        val response = repository.getAttendanceRecords(dayOfWeek, studentID)
//        return recordsValidator.validateData(response)
//    }
//
//    private suspend fun getStudentTable(cardID: String): ValidationStatus<StudentTableDTO> {
//        val response = repository.getStudentTable(cardID)
//        return groupsValidator.validateData(response)
//    }
//
//    private fun compareRecords(records: List<RecordDTO>, table: List<GroupsDTO>, dayOfWeek: String): List<SessionItem> {
//        val sessions = mutableListOf<SessionItem>()
//        val groupsList = table[0].allGroupData
//
//        if (groupsList.isEmpty()) return sessions
//
//        for (index in groupsList.indices) {
//            // دا علشان نظبط حالة الحضور
//            var isFounded = false
//            records.forEach{ record ->
//                if (groupsList[index]._id == record.group) {
//                    isFounded = true
//                    sessions.add(
//                        SessionItem(
//                            courseName = record.group_data[0].tutorCourse.courseData.name,
//                            centerName = record.group_data[0].center.name,
//                            groupNumber = record.group_data[0].groupNumber.toString(),
//                            tutorName = record.group_data[0].tutorCourse.tutor.fullname,
//                            isAttended = record.isAttended,
//                            dayOfWeek = record.group_data[0].dayOfWeek,
//                            isPending = false,
//                            createdAt = record.group_data[0].startTime,
//                        )
//                    )
//                }
//            }
//            //دا علشان نظبط حالتي الpending و الغياب
//            if (!isFounded) {
//                sessions.add(
//                    SessionItem(
//                        courseName = groupsList[index].tutorCourse.courseData.name,
//                        centerName = groupsList[index].center.name,
//                        groupNumber = groupsList[index].groupNumber.toString(),
//                        tutorName = groupsList[index].tutorCourse.tutor.fullname,
//                        dayOfWeek = groupsList[index].dayOfWeek,
//                        isAttended = false,
//                        isPending = (groupsList[index].dayOfWeek >= Integer.valueOf(dayOfWeek)),
//                        createdAt = groupsList[index].startTime
//                    )
//                )
//            }
//        }
//        // دا علشان نظبط حالة التعويض
//        records.forEach { record ->
//            var notFound = true
//            groupsList.forEach { group ->
//                if (record.group == group._id) notFound = false
//            }
//            if (notFound) {
//                sessions.add(
//                    SessionItem(
//                        courseName = record.group_data[0].tutorCourse.courseData.name,
//                        centerName = record.group_data[0].center.name,
//                        groupNumber = record.group_data[0].groupNumber.toString(),
//                        tutorName = record.group_data[0].tutorCourse.tutor.fullname,
//                        dayOfWeek = record.group_data[0].dayOfWeek,
//                        isAttended = true,
//                        createdAt = record.group_data[0].startTime
//                    )
//                )
//            }
//        }
//        return sessions
//    }
//}