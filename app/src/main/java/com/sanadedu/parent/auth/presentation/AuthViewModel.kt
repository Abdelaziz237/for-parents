package com.sanadedu.parent.auth.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.data.repository.AuthRepositoryImpl
import com.sanadedu.parent.auth.domain.usecases.AuthUseCases
import com.sanadedu.parent.core.data.ValidationCodes.ACCESS_DENIED
import com.sanadedu.parent.core.data.ValidationCodes.BAD_NETWORK
import com.sanadedu.parent.core.data.ValidationCodes.EMAIL_ALREADY_EXISTS
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_REGISTRATION
import com.sanadedu.parent.core.data.ValidationCodes.NEEDS_VERIFICATION
import com.sanadedu.parent.core.data.ValidationCodes.PARSING_EXCEPTION
import com.sanadedu.parent.core.data.ValidationCodes.UNAUTHORIZED
import com.sanadedu.parent.core.data.ValidationCodes.WRONG_PASSWORD
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.ShowToast
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import kotlinx.coroutines.launch

open class AuthViewModel: ViewModel() {

    private val repository = AuthRepositoryImpl()
    private val showToast = ShowToast()
    private val authUseCases = AuthUseCases(repository)

    fun onEvent(event: AuthEvent, callback: (isValid: Boolean) -> Unit) {
        viewModelScope.launch {
            when(event) {
                is AuthEvent.VerifyOtp -> {
                    verifyOtp(event.email, event.otp, callback)
                }

                is AuthEvent.ReSendOtp -> {
                    resendOtp(event.email, callback)
                }
            }
        }
    }

    private fun verifyOtp(
        email:String,
        otp: String,
        callback: (isVerified: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            when (
                val status = authUseCases.verifyOtp(
                    email = email,
                    otp = otp,
                )
            ) {
                is ValidationStatus.Valid -> {
                    showToast(
                        message = R.string.verified_successfully,
                        type = ToastType.SuccessToast
                    )
                    callback(true)
                }

                is ValidationStatus.NotValid -> {
                    showErrorToastMessage(status.cause)
                    callback(false)
                }
            }
        }
    }

    private fun resendOtp(
        email:String,
        callback: (isSent: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            when (
                val status = authUseCases.resendOtp(
                    email = email
                )
            ) {
                is ValidationStatus.Valid -> {
                    showToast(
                        message = R.string.otp_resent,
                        type = ToastType.SuccessToast
                    )
                    callback(true)
                }

                is ValidationStatus.NotValid -> {
                    showErrorToastMessage(status.cause)
                    callback(false)
                }
            }
        }
    }

    protected fun showErrorToastMessage(cause: Int) {
        val messageId: Int = when (cause) {
            UNAUTHORIZED -> {
                R.string.unauthorized
            }

            PARSING_EXCEPTION -> {
                R.string.fatal_error
            }

            ACCESS_DENIED -> {
                R.string.refused
            }

            WRONG_PASSWORD -> {
                R.string.wrong_password
            }

            EMAIL_ALREADY_EXISTS -> {
                R.string.email_already_exists
            }

            BAD_NETWORK -> {
                R.string.bad_network
            }

            NEEDS_VERIFICATION -> {
                R.string.needs_verification
            }

            NEEDS_REGISTRATION -> {
                R.string.needs_registration
            }

            else -> {
                R.string.unknown
            }
        }
        showToast(
            message = messageId,
            type = ToastType.ErrorToast
        )
    }

    protected fun showToastMessage(message: Int, type: ToastType) {
        showToast(
            message = message,
            type = type
        )
    }
}