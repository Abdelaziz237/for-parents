package com.sanadedu.parent.feed.data

import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastViewModel

object ToastManager {
    private var viewModel: ToastViewModel? = null

    fun initializeToastManager(v: ToastViewModel) {
        if (viewModel == null) {
            viewModel = v
        }
    }

    fun get(): ToastViewModel? {
        return if (viewModel != null) viewModel as ToastViewModel
        else null
    }
}