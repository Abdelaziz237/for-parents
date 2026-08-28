package com.sanadedu.parent.feed.presentation.home.states

import com.sanadedu.parent.feed.presentation.home.items.SessionItem
import com.sanadedu.parent.feed.presentation.home.items.StudentItem

data class HomeFeedState(
    val students: List<StudentItem> = emptyList(),
    val sessions: List<SessionItem> = emptyList(),
    val isHomeActive: Boolean = true,
    val isProfileActive: Boolean = false,
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false
)
