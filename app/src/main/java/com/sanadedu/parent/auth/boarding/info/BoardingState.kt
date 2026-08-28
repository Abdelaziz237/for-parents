package com.sanadedu.parent.auth.boarding.info

import com.sanadedu.parent.R

data class BoardingState(
    val image: Int = R.drawable.property1,
    val header: Int = R.string.header_1,
    val body: Int = R.string.body_1,
    val progress: Float = 0.33f
)
