package com.sanadedu.parent.core.presentation

import android.util.Log
import java.time.LocalDate

fun getCurrentDay(): Int {
    return when (val kotlinIsoDay = LocalDate.now().dayOfWeek.value) {
        6 -> {
            0
        }
        7 -> {
            1
        }
        else -> {
            kotlinIsoDay + 1
        }
    }
}

fun getDayString(day: Int): String {
    val days = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    val today = getCurrentDay()

    Log.e("Day: ", "tomorrow ${today + 1}, $today, yesterday ${today - 1}")

    return when (day) {
        today - 1 -> "Yesterday"
        today -> "Today"
        today + 1 -> "Tomorrow"
        else -> days[day]
    }
}