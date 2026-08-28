package com.sanadedu.parent.feed.data.qr_data

import android.graphics.Bitmap
import android.graphics.Rect

data class BarcodeResult(
    val result: String,
    val boundingBox: Rect?,
    val lastFrame: Bitmap
)
