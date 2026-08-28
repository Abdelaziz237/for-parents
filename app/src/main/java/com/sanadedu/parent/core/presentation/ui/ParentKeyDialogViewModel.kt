package com.sanadedu.parent.core.presentation.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.feed.data.repository.HomeFeedRepositoryImpl
import com.sanadedu.parent.core.domain.usecases.AddStudent
import kotlinx.coroutines.launch


class ParentKeyDialogViewModel : ViewModel() {

    private val repository = HomeFeedRepositoryImpl(LocalClient.getClientViewModel().userCredentialsState.value.authToken)
    private val addStudent = AddStudent(repository)

    private val _state = mutableStateOf(ParentKeyDialogState())
    val state: State<ParentKeyDialogState> = _state

    fun onEvent(event: ParentKeyDialogEvents, callback: () -> Unit = {}) {
        when(event) {
            ParentKeyDialogEvents.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true,
                )
            }

            is ParentKeyDialogEvents.AddStudentByKey -> {
                viewModelScope.launch {
                    when (addStudent(parentKey = event.parentKey)) {
                        is ValidationStatus.Valid -> {
                            callback()
                        }
                        is ValidationStatus.NotValid -> {

                        }
                    }
                }.invokeOnCompletion { onEvent(ParentKeyDialogEvents.StopLoading) }
            }

            ParentKeyDialogEvents.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }
}