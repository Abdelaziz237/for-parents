package com.sanadedu.parent.feed.data.qr_data

import android.graphics.Bitmap
import android.graphics.Rect

data class QrState(
    val code: String = NULL_INPUT,
    val boundedBox: Rect? = null,
    val imageBitmap: Bitmap? = null,
    val isCameraFrozen: Boolean = false,
    val isLoading: Boolean = true,
    val isModuleInstalled: Boolean = false
)