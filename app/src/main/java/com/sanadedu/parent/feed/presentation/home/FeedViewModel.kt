package com.sanadedu.parent.feed.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.utils.showToast
import com.sanadedu.parent.core.presentation.getCurrentDay
import com.sanadedu.parent.feed.data.GlobeVars.NOT_CREATED
import com.sanadedu.parent.feed.data.repository.HomeFeedRepositoryImpl
import com.sanadedu.parent.feed.domain.usecases.FeedUseCases
import com.sanadedu.parent.feed.domain.usecases.GetAttendanceForWeek
import com.sanadedu.parent.feed.domain.usecases.GetStudents
import com.sanadedu.parent.feed.domain.util.orderByDay
import com.sanadedu.parent.feed.domain.util.toSessionItems
import com.sanadedu.parent.feed.presentation.home.events.HomeFeedEvent
import com.sanadedu.parent.feed.presentation.home.items.SessionItem
import com.sanadedu.parent.feed.presentation.home.items.StudentItem
import com.sanadedu.parent.feed.presentation.home.states.HomeFeedState
import kotlinx.coroutines.launch

class FeedViewModel: ViewModel() {
    private val repository = HomeFeedRepositoryImpl(
        authToken = LocalClient.getClientViewModel().userCredentialsState.value.authToken
    )
    private val feedUseCases = FeedUseCases(
        getStudents = GetStudents(repository),
        getAttendanceForWeek = GetAttendanceForWeek(repository)
    )

    private val _state = mutableStateOf(HomeFeedState())
    val state: State<HomeFeedState> = _state

    private val attendanceMap: MutableMap<String, List<SessionItem>> = mutableMapOf()

    init {
        onEvent(HomeFeedEvent.StartLoading)
        viewModelScope.launch {
            val students = getStudents()
            students.forEach { student ->
                attendanceMap[student.cardID] = getSessions(dayOfWeek = getCurrentDay().toString(), cardID = student.cardID)
            }
            _state.value = state.value.copy(
                students = students,
                isLoading = false,
                isEmpty = students.isEmpty()
            )
        }
    }

    fun onEvent(event: HomeFeedEvent) {
        when(event) {
            is HomeFeedEvent.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }
            is HomeFeedEvent.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
            is HomeFeedEvent.GetStudents -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        students = getStudents()
                    )
                }
            }
            is HomeFeedEvent.GetAttendanceRecord -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        sessions = attendanceMap[event.cardID] ?: emptyList()
                    )
                }
            }
            is HomeFeedEvent.NavigateToProfileScreen -> {
                _state.value = state.value.copy(
                    isHomeActive = false,
                    isProfileActive = true
                )
            }
            is HomeFeedEvent.NavigateToNotificationsScreen -> {
                _state.value = state.value.copy(
                    isHomeActive = false
                )
            }
            is HomeFeedEvent.BackToHomeScreen -> {
                _state.value = state.value.copy(
                    isHomeActive = true,
                    isProfileActive = false
                )
            }
        }
    }

    private suspend fun getStudents(): List<StudentItem> {
        return when (val result = feedUseCases.getStudents()) {
            is ValidationStatus.Valid -> {
                result.data.data.map { student ->
                    Log.e("Students", "$student")
                    StudentItem(
                        studentID = student._id,
                        cardID = student.card ?: NOT_CREATED,
                        name = student.fullname,
                        code = student.code,
                        coursesCount = student.coursesCount.toString(),
                        avgAttendance = "?",
                        image = ""
                    )
                }
            }
            is ValidationStatus.NotValid -> {
                showToast(result.cause)
                emptyList()
            }
        }
    }

    private suspend fun getSessions(dayOfWeek: String, cardID: String): List<SessionItem> {
        return when (val result = feedUseCases.getAttendanceForWeek(dayOfWeek = dayOfWeek, cardId = cardID)) {
            is ValidationStatus.Valid -> {
                result.data.sessions.toSessionItems()
            }
            is ValidationStatus.NotValid -> {
                showToast(result.cause)
                emptyList()
            }
        }.orderByDay()
    }
}