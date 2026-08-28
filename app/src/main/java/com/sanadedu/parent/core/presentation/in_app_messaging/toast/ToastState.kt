package com.sanadedu.parent.core.presentation.in_app_messaging.toast

import androidx.compose.ui.graphics.Color

data class ToastState(
    val message: Int = -1,
    val type: ToastType? = null,
    val background: Color = Color.White,
    val icon: Int = -1,
    val isShown: Boolean = false
)