package com.sanadedu.parent.auth.presentation.sign_in

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.domain.util.AuthUseCasesManager
import com.sanadedu.parent.auth.presentation.AuthViewModel
import com.sanadedu.parent.auth.presentation.sign_in.data.AuthError
import com.sanadedu.parent.auth.presentation.sign_in.info.LoginPageEvent
import com.sanadedu.parent.auth.presentation.sign_in.info.PageState
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_REGISTRATION
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_VERIFICATION
import com.sanadedu.parent.core.data.ValidationCodes.UNAUTHORIZED
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import kotlinx.coroutines.launch

class SignInViewModel: AuthViewModel() {

    private val emailOrCodeRegex = "(^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})\$)|(^P[0-9]{7}\$)".toRegex()
    private val passwordRegex = "^(?=\\S*\\d)(?=\\S*[$!@%&_])\\S{8,30}$".toRegex()

    private val _state = mutableStateOf(PageState())
    val state: State<PageState> = _state

    private val signIn = AuthUseCasesManager.getAuthUseCases().signIn
    private val clientViewModel = LocalClient.getClientViewModel()
    private val clientStatus = clientViewModel.userCredentialsState.value.clientStatus

    fun onEvent(event: LoginPageEvent, onSuccess: () -> Unit = {}, onError: (cause: AuthError) -> Unit = {}) {
        when (event) {
            is LoginPageEvent.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            is LoginPageEvent.Login -> {
                if (hasValidCredentials(event.email, event.password).not()) {
                    onEvent(LoginPageEvent.StopLoading)
                    return
                }

                viewModelScope.launch {
                    when (val result = signIn(event.email, event.password)) {
                        is ValidationStatus.Valid -> {
                            val user = result.data.data
                            val token = result.data.token

                            saveUserCredentials(token = token, username = user.fullname, profileImage = user.profileImage, onCompleted = { onSuccess() })

                            showToastMessage( message = R.string.success, type = ToastType.SuccessToast )
                        }

                        is ValidationStatus.NotValid -> {
                            val cause = result.cause
                            if (cause == UNAUTHORIZED) {

                                showErrorToastMessage(NEEDS_VERIFICATION)
                                onError(AuthError.NEED_OTP_VERIFICATION)

                            } else if (clientStatus == ClientStatus.NEEDS_REGISTRATION.name) {

                                showErrorToastMessage(NEEDS_REGISTRATION)
                                onError(AuthError.INCOMPLETE_REGISTRATION)

                            } else {
                                showErrorToastMessage(cause)
                            }
                        }
                    }
                }.invokeOnCompletion { onEvent(LoginPageEvent.StopLoading) }
            }

            is LoginPageEvent.ShowErrorMessage -> {
                showToastMessage(
                    message = event.message,
                    type = ToastType.ErrorToast
                )
            }

            is LoginPageEvent.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }

        }
    }

    private fun saveUserCredentials(token: String, username: String, profileImage: String, onCompleted: () -> Unit = {}) {
        clientViewModel.setUserCredentials(
            token = token,
            username = username,
            profileImage = profileImage,
            callback = { LocalClient.getClientViewModel().updateClientStatus(ClientStatus.LOGGED); onCompleted() }
        )
    }

    private fun hasValidCredentials(emailOrCode: String, password: String): Boolean {
        if (!emailOrCodeRegex.matches(emailOrCode)) {
            showToastMessage(
                message = R.string.email_validation_error_toast_message,
                type = ToastType.ErrorToast
            )
            return false
        }

        if (!passwordRegex.matches(password)) {
            showToastMessage(
                message = R.string.password_validation_error_toast_message,
                type = ToastType.ErrorToast
            )
            return false
        }

        return true
    }
}