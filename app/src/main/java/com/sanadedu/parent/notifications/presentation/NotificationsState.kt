package com.sanadedu.parent.notifications.presentation

data class NotificationsState(
    val isLoading: Boolean = true,
    val notifications: List<NotificationItem> = emptyList()
)
