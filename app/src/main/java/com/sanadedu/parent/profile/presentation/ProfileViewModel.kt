package com.sanadedu.parent.profile.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.ShowToast
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import com.sanadedu.parent.profile.data.repository.ProfileRepositoryImpl
import com.sanadedu.parent.profile.domain.usecases.ProfileUseCases
import com.sanadedu.parent.profile.presentation.info.ProfileEvent
import com.sanadedu.parent.profile.presentation.info.ProfilePageState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val restartTheApp: () -> Unit
): ViewModel() {

    private val _state = mutableStateOf(ProfilePageState())
    val state: State<ProfilePageState> = _state

    private val clientViewModel = LocalClient.getClientViewModel()
    private val userCredentials = clientViewModel.userCredentialsState.value
    private val profileUseCases = ProfileUseCases(repo = ProfileRepositoryImpl(authToken = userCredentials.authToken))
    private val showToast = ShowToast()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            ProfileEvent.GetProfileInfo -> {
                viewModelScope.launch {
                    when (val result = profileUseCases.getProfileInfo()) {
                        is ValidationStatus.Valid -> {
                            val info = result.data.data
                            _state.value = state.value.copy(
                                username = info.fullname,
                                parentCode = info.code,
                                studentsCount = info.studentsNumber,
                                phoneNumber = info.phoneNumber
                            )
                        }
                        is ValidationStatus.NotValid -> {
                            _state.value = state.value.copy(
                                showTryAgain = true
                            )
                        }
                    }
                }.invokeOnCompletion { onEvent(ProfileEvent.StopLoading) }
            }

            ProfileEvent.DeleteAccount -> {
                viewModelScope.launch {
                    when (profileUseCases.deleteAccount()) {
                        is ValidationStatus.Valid -> {
                            showToast(
                                message = R.string.account_deleted,
                                type = ToastType.InfoToast
                            )
                            onEvent(ProfileEvent.Logout)
                        }
                        is ValidationStatus.NotValid -> {
                            showToast(
                                message = R.string.account_deleted_failed,
                                type = ToastType.ErrorToast
                            )
                        }
                    }
                }
            }

            ProfileEvent.Logout -> {
                clientViewModel.logout(
                    callback = {
                        restartTheApp()
                    }
                )
            }

            ProfileEvent.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    class ProfileViewModelFactory(private val restartTheApp: () -> Unit): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(restartTheApp = restartTheApp) as T
        }
    }
}