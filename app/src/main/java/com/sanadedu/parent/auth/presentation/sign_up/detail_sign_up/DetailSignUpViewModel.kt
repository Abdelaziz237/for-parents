package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.data.repository.AuthRepositoryImpl
import com.sanadedu.parent.auth.domain.model.User
import com.sanadedu.parent.auth.domain.usecases.Register
import com.sanadedu.parent.auth.domain.util.ImageUploader
import com.sanadedu.parent.auth.domain.util.PreSignedUrlFetcher
import com.sanadedu.parent.auth.presentation.AuthViewModel
import com.sanadedu.parent.auth.presentation.sign_in.info.PageState
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.info.RegisterFormEvent
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import kotlinx.coroutines.launch

class DetailSignUpViewModel(email: String) : AuthViewModel() {
    private val _state = mutableStateOf(PageState())
    val state: State<PageState> = _state

    private val _formState = mutableStateOf(User.UserForRegister(email = email))
    val formState: State<User.UserForRegister> = _formState

    private val repository = AuthRepositoryImpl()
    private val register = Register(repository)

    fun onEvent(event: RegisterFormEvent, callback: () -> Unit) {
        when (event) {
            is RegisterFormEvent.StartLoading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }
            is RegisterFormEvent.StopLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
            is RegisterFormEvent.SubmitPhoneNumber -> {
                if (event.fullNumber != "") {
                    _formState.value = formState.value.copy(
                        phoneNumber = event.fullNumber
                    )
                    callback()
                } else showToastMessage(R.string.all_fields_required, ToastType.ErrorToast)
            }
            is RegisterFormEvent.SubmitAddress -> {
                if (event.address != "" || event.city != "" || event.government != "") {
                    _formState.value = formState.value.copy(
                        governorate = event.government,
                        city = event.city,
                        address = event.address
                    )
                    callback()
                } else showToastMessage(R.string.all_fields_required, ToastType.ErrorToast)
            }

            is RegisterFormEvent.SubmitBirthdateAndGender -> {
                if (event.birthdate != "" && event.gender != "n") {
                    val birthdateSplit = event.birthdate.split('/')
                    Log.e("Birthdate", birthdateSplit.toString())
                    _formState.value = formState.value.copy(
                        birthDate = "${birthdateSplit[2]}-${birthdateSplit[1]}-${birthdateSplit[0]}",
                        gender = event.gender
                    )
                    callback()
                } else showToastMessage(R.string.all_fields_required, ToastType.ErrorToast)
            }

            is RegisterFormEvent.UploadImage -> {
                viewModelScope.launch {
                    when(val result = PreSignedUrlFetcher.fetch(event.email)) {
                        is ValidationStatus.Valid -> {
                            val preSignedUrl = result.data.url
                            val mimeType = event.mimeType.split('/')[1]
                            ImageUploader.uploadImage(preSignedUrl = preSignedUrl, event.imageFile).let { isUploaded ->
                                if (isUploaded) {
                                    val imageLink = "${ImageUploader.BASE_IMAGE_URL}/${ImageUploader.BASE_VENDOR}/${event.imageFile.name}.${mimeType}"
                                    Log.e("ImageLink", imageLink)

                                    _formState.value = formState.value.copy(
                                        profileImage = imageLink
                                    )

                                    showToastMessage(
                                        message = R.string.upload_image_success,
                                        type = ToastType.ErrorToast
                                    )
                                } else {
                                    showToastMessage(
                                        message = R.string.upload_image_failed,
                                        type = ToastType.ErrorToast
                                    )
                                }
                            }
                        }
                        is ValidationStatus.NotValid -> {
                            showErrorToastMessage(result.cause)
                        }
                    }
                }.invokeOnCompletion { callback() }
            }

            is RegisterFormEvent.SubmitImage -> {
                _formState.value = formState.value.copy(
//                    profileImage = event.profileImagePath
                )
                callback()
            }

            is RegisterFormEvent.SubmitForm -> {
                viewModelScope.launch {
                    val status = register(
                        formState.value
                    )
                    when (status) {
                        is ValidationStatus.NotValid -> {
                            super.showErrorToastMessage(status.cause)
                        }
                        is ValidationStatus.Valid -> {
                            super.showToastMessage(R.string.account_created, ToastType.SuccessToast)
                            callback()
                        }
                    }
                }
                Log.e("Form", formState.value.toString())
            }
        }
    }
}

class DetailSignUpViewModelFactory(private val email: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailSignUpViewModel::class.java)) {
            return DetailSignUpViewModel(email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}