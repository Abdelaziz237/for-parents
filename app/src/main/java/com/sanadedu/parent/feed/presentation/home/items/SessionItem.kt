package com.sanadedu.parent.feed.presentation.home.items


data class SessionItem(
    val courseName: String,
    val centerName: String,
    val groupNumber: String,
    val tutorName: String,
    val isAttended: Boolean,
    val isPending: Boolean = false,
    val createdAt: String,
    var dayOfMonth: Int = -1,
    var dayOfWeek: Int = -1,
    var hour: String = "??"
)
