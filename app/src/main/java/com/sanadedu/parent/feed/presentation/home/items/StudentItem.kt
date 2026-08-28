package com.sanadedu.parent.feed.presentation.home.items

data class StudentItem(
    val studentID: String,
    val cardID: String,
    val name: String,
    val code: String,
    val coursesCount: String,
    val avgAttendance: String,
    val image: String,
)