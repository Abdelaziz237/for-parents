package com.sanadedu.parent.core.presentation.in_app_messaging.toast

sealed class ToastType {
    object SuccessToast: ToastType()
    object InfoToast: ToastType()
    object ErrorToast: ToastType()
}