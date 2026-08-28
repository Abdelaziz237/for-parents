package com.sanadedu.parent.student.representation.single_course

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils.DefaultDateUtil
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.utils.showToast
import com.sanadedu.parent.student.domain.usecases.StudentUseCasesManager
import com.sanadedu.parent.student.domain.util.CourseDataObject
import com.sanadedu.parent.student.domain.util.StudentDataObject
import com.sanadedu.parent.student.representation.single_course.data.ExamItemForCourse
import com.sanadedu.parent.student.representation.single_course.data.SessionItemForCourse
import kotlinx.coroutines.launch

class CourseViewModel: ViewModel() {

    private val _state = mutableStateOf(CourseState())
    val state: State<CourseState> = _state

    private val studentUseCases = StudentUseCasesManager.getStudentUseCase()
    private val cardId = StudentDataObject.getStudent().cardId
    private val currentCourse = CourseDataObject.getCourse()

    fun onEvent(event: SingleCourseEvents) {
        when(event) {
            SingleCourseEvents.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            SingleCourseEvents.GetCourseData -> {
                _state.value = state.value.copy(
                    course = currentCourse
                )
                viewModelScope.launch {
                    getSessions(); getExams()
                }.invokeOnCompletion { onEvent(SingleCourseEvents.StopLoading) }
            }

            SingleCourseEvents.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    private suspend fun getExams() {
        var totalGrades = 0
        var totalMarks = 0
        when(val result = studentUseCases.getCourseExams(cardId = cardId, tutorCourseId = currentCourse.courseId)) {
            is ValidationStatus.Valid -> {
                val exams = result.data.data
                _state.value = state.value.copy(
                    exams = exams.map {
                        totalGrades += if (it.grade == -1) 0 else it.grade
                        totalMarks += it.expectedMark

                        ExamItemForCourse(
                            examName = it.title,
                            tutorName = it.createdBy.fullname,
                            grade = if (it.grade == -1) it.grade.toFloat() else (it.grade.toFloat() / it.expectedMark.toFloat()),
                            centerName = currentCourse.centerName,
                            tags = it.topicData.map { topic -> topic.name },
                            examNumber = it.examNumber.toString()
                        )
                    },
                    avgExams = (totalGrades.toFloat() / totalMarks.toFloat())
                )
            }
            is ValidationStatus.NotValid -> {
                showToast(result.cause)
            }
        }
    }

    private suspend fun getSessions() {
        var totalAttendance = 0
        when(val result = studentUseCases.getCourseSessions(cardId = cardId, tutorCourseId = currentCourse.courseId)) {
            is ValidationStatus.Valid -> {
                val sessions = result.data.data
                _state.value = state.value.copy(
                    sessions = sessions.map {
                        if (it.isAttended == "true") totalAttendance++
                        SessionItemForCourse(
                            sessionName = it.name,
                            courseName = currentCourse.courseName,
                            isAttended = it.isAttended == "true",
                            isPending = it.isAttended == "pending",
                            tutorName = it.createdBy.fullname,
                            sessionFees = (it.sessionFees ?: "").toString(),
                            attendedDay = it.attendTime?.let { attendTime -> DefaultDateUtil.convertTimestampToFormattedDate(attendTime) } ?: "",
                            attendedHour = it.attendTime?.let { attendTime -> DefaultDateUtil.formatHourFromTimestamp(attendTime) } ?: "",
                            groupNumber = it.groupData?.groupNumber?.toString() ?: "N/A"
                        )
                    },
                    avgAttendance = (totalAttendance.toFloat() / sessions.size.toFloat())
                )
            }
            is ValidationStatus.NotValid -> {
                showToast(result.cause)
            }
        }

    }
}