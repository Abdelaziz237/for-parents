package com.sanadedu.parent.notifications.presentation

data class NotificationItem(
    val senderImage: String,
    val senderName: String,
    val centerName: String,
    val title: String,
    val content: String,
    val date: String
)
