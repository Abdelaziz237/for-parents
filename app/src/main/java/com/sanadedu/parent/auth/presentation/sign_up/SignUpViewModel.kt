package com.sanadedu.parent.auth.presentation.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.domain.util.AuthUseCasesManager
import com.sanadedu.parent.auth.presentation.AuthViewModel
import com.sanadedu.parent.auth.presentation.sign_in.data.AuthError
import com.sanadedu.parent.auth.presentation.sign_in.info.PageState
import com.sanadedu.parent.auth.presentation.sign_up.info.SignUpPageEvent
import com.sanadedu.parent.auth.presentation.sign_up.info.SignUpPageState
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_REGISTRATION
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_VERIFICATION
import com.sanadedu.parent.core.data.ValidationCodes.UNAUTHORIZED
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import kotlinx.coroutines.launch

class SignUpViewModel: AuthViewModel() {
    private val emailRegex = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})\$".toRegex()

    private val clientStatus = LocalClient.getClientViewModel().userCredentialsState.value.clientStatus

    private val _state = mutableStateOf(PageState())
    val state: State<PageState> = _state

    private val _registerState = mutableStateOf(SignUpPageState())
    val registerState: State<SignUpPageState> = _registerState

    private val verifyEmail = AuthUseCasesManager.getAuthUseCases().verifyEmail

    fun onEvent(event: SignUpPageEvent, onSuccess: () -> Unit = {}, onError: (cause: AuthError) -> Unit = {}) {
        when (event) {
            is SignUpPageEvent.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            is SignUpPageEvent.VerifyUser -> {
                if (hasValidEmail(event.email).not()) {
                    onEvent(SignUpPageEvent.StopLoading)
                    return
                }

                viewModelScope.launch {
                    when (
                        val result = verifyEmail(
                            fullName = event.fullName,
                            email = event.email,
                            password = event.password,
                            emailCampaigns = event.emailCampaigns
                        )
                    ) {
                        is ValidationStatus.Valid -> {
                            showToastMessage(message = R.string.welcome, type = ToastType.SuccessToast)
                            onSuccess()
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
                }.invokeOnCompletion { onEvent(SignUpPageEvent.StopLoading) }
            }

            is SignUpPageEvent.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun hasValidEmail(email: String): Boolean {
        if (!emailRegex.matches(email)) {
            showToastMessage(
                message = R.string.email_only_validation_error_toast_message,
                type = ToastType.ErrorToast
            )
            return false
        }

        return true
    }
}