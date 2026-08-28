package com.sanadedu.parent.student.representation.student_courses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.utils.showToast
import com.sanadedu.parent.student.domain.usecases.StudentUseCases
import com.sanadedu.parent.student.domain.usecases.StudentUseCasesManager
import com.sanadedu.parent.student.domain.util.StudentDataObject
import com.sanadedu.parent.student.representation.student_courses.info.StudentScreenEvents
import com.sanadedu.parent.student.representation.student_courses.info.StudentState
import com.sanadedu.parent.student.representation.student_courses.info.data.CenterItem
import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem
import kotlinx.coroutines.launch

class SingleStudentViewModel: ViewModel() {

    private val _state = mutableStateOf(StudentState())
    val state: State<StudentState> = _state

    private val userCredentials = LocalClient.getClientViewModel().userCredentialsState.value
    private val studentUseCases: StudentUseCases

    init {
        StudentUseCasesManager.initialize(
            userCredential = userCredentials
        )
        studentUseCases = StudentUseCasesManager.getStudentUseCase()
    }

    fun onEvent(event: StudentScreenEvents) {
        when (event) {
            StudentScreenEvents.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            StudentScreenEvents.GetCenters -> {
                viewModelScope.launch {
                    when (val result = studentUseCases.getCenters(StudentDataObject.getStudent().cardId)) {
                        is ValidationStatus.Valid -> {
                            val centers = result.data.data
                            _state.value = state.value.copy(
                                centers = centers.map {
                                    CenterItem(
                                        id = it._id,
                                        name = it.name,
                                        code = it.code
                                    )
                                }
                            )
                        }
                        is ValidationStatus.NotValid -> {
                            showToast(result.cause)
                        }
                    }
                }
            }

            is StudentScreenEvents.GetCourses -> {
                viewModelScope.launch {
                    when (val result = studentUseCases.getCourses(StudentDataObject.getStudent().cardId)) {
                        is ValidationStatus.Valid -> {
                            val courses = result.data.data
                            _state.value = state.value.copy(
                                courses = courses.map {
                                    CourseItem(
                                        courseId = it.tutorCourse._id,
                                        centerCode = it.centerCode,
                                        centerName = it.centerName,
                                        courseName = it.tutorCourse.courseData.name,
                                        tutorName = it.tutorCourse.tutor.fullname,
                                        sessionsCount = it.sessions_count.toString(),
                                        courseImg = it.tutorCourse.courseData.image
                                    )
                                }
                            )
                        }
                        is ValidationStatus.NotValid -> {
                            showToast(result.cause)
                        }
                    }
                }.invokeOnCompletion { onEvent(StudentScreenEvents.StopLoading) }
            }

            is StudentScreenEvents.FilterCoursesByCenter -> {
                _state.value = state.value.copy(
                    courses = state.value.courses.filter {
                        it.centerCode == event.centerCode
                    }
                )
            }

            StudentScreenEvents.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }

}