package com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils

interface DateUtil {
    fun convertFormattedDateToTimestamp(date: String): String
    fun convertTimestampToFormattedDate(timestamp: String): String
    fun getEndOfCurrentDay(): String
    fun getEndOfCurrentWeek(): String
    fun getStartOfCurrentWeek(): String
    fun convertDateTimeToLong(dateTime: String): Long
    fun formatDateTime(inputDate: String): String
    fun formatHourFromTimestamp(timestamp: String): String
}
