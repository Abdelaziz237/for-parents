package com.sanadedu.parent.feed.domain.util

import com.sanadedu.parent.feed.presentation.home.items.SessionItem

fun List<SessionItem>.orderByDay(): List<SessionItem> {
    return this.sortedBy { it.dayOfWeek }
}