package com.sanadedu.parent.core.domain.usecases

import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastEvent
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastState
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastType
import com.sanadedu.parent.feed.data.ToastManager

class ShowToast {
    operator fun invoke(message: Int, type: ToastType) {
        val manager = ToastManager.get()
        manager?.onEvent(ToastEvent.ShowToast(message, type))
    }
}