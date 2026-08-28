package com.sanadedu.parent.notifications.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils.DefaultDateUtil
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.utils.showToast
import com.sanadedu.parent.student.domain.usecases.StudentUseCasesManager
import com.sanadedu.parent.student.domain.util.StudentDataObject
import kotlinx.coroutines.launch

class NotificationsViewModel: ViewModel() {

    private val _state = mutableStateOf(NotificationsState())
    val state: State<NotificationsState> = _state

    private val studentUseCases = StudentUseCasesManager.getStudentUseCase()

    fun getNotifications() {
        viewModelScope.launch {
            when (val result = studentUseCases.getNotifications(cardId = StudentDataObject.getStudent().cardId)) {
                is ValidationStatus.Valid -> {
                    val notifications = result.data.data
                    _state.value = state.value.copy(
                        notifications = notifications.map {
                            NotificationItem(
                                senderImage = it.createdBy.profileImage,
                                senderName = it.createdBy.fullname,
                                centerName = it.name,
                                title = it.title,
                                content = it.description,
                                date = DefaultDateUtil.convertTimestampToFormattedDate(it.updatedAt)
                            )
                        },
                        isLoading = false
                    )
                }
                is ValidationStatus.NotValid -> {
                    showToast(result.cause)
                }
            }
        }
    }
}
