package com.sanadedu.parent.core.presentation.in_app_messaging.toast

sealed class ToastEvent {
    data class ShowToast(val message: Int, val type: ToastType): ToastEvent()
    object DismissToast: ToastEvent()
}