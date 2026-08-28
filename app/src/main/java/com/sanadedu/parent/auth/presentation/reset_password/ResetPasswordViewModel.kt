package com.sanadedu.parent.auth.presentation.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.domain.util.AuthUseCasesManager
import com.sanadedu.parent.auth.presentation.AuthViewModel
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import kotlinx.coroutines.launch

class ResetPasswordViewModel: AuthViewModel() {
    private val emailOrCodeRegex = "(^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})\$)|(^P[0-9]{7}\$)".toRegex()

    private val _state = mutableStateOf(false)
    val state: State<Boolean> = _state

    private val sendResetPasswordLink = AuthUseCasesManager.getAuthUseCases().forgotPassword

    fun onEvent(event: ForgotPasswordPageEvents, callback: (isValid: Boolean) -> Unit = {}) {
        when(event) {
            is ForgotPasswordPageEvents.StartLoading -> {
                _state.value = true
            }

            is ForgotPasswordPageEvents.SendResetPasswordLink -> {
                if (hasValidEmail(event.email).not()) {
                    onEvent(ForgotPasswordPageEvents.StopLoading)
                    return
                }

                var isValid = false
                viewModelScope.launch {
                    when (sendResetPasswordLink(email = event.email)) {
                        is ValidationStatus.Valid -> {
                            isValid = true
                            showToastMessage(
                                message = R.string.reset_password_link_sent,
                                type = ToastType.SuccessToast
                            )
                        }

                        is ValidationStatus.NotValid -> {
                            showToastMessage(
                                message = R.string.something_went_wrong,
                                type = ToastType.ErrorToast
                            )
                        }
                    }
                }.invokeOnCompletion { onEvent(ForgotPasswordPageEvents.StopLoading); callback(isValid) }
            }

            is ForgotPasswordPageEvents.StopLoading -> {
                _state.value = false
            }
        }
    }

    private fun hasValidEmail(email: String): Boolean {
        if (!emailOrCodeRegex.matches(email)) {
            showToastMessage(
                message = R.string.email_validation_error_toast_message,
                type = ToastType.ErrorToast
            )
            return false
        }

        return true
    }
}