package com.sanadedu.parent.feed.presentation.home.events

sealed class HomeFeedEvent {
    data object GetStudents: HomeFeedEvent()
    data class GetAttendanceRecord(val cardID: String): HomeFeedEvent()
    data object StartLoading: HomeFeedEvent()
    data object StopLoading: HomeFeedEvent()
    data object NavigateToProfileScreen: HomeFeedEvent()
    data object NavigateToNotificationsScreen: HomeFeedEvent()
    data object BackToHomeScreen: HomeFeedEvent()
}