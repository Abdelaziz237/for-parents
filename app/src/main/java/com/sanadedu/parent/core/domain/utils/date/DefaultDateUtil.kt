package com.sanad.studentsapp.home.exams.domain.usecases.get_all_exams.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

object DefaultDateUtil : DateUtil {
    override fun convertFormattedDateToTimestamp(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val localDate = LocalDate.parse(date, formatter)
        val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return localDate.atStartOfDay().format(isoFormatter)
    }

    override fun convertTimestampToFormattedDate(timestamp: String): String {
        val zonedDateTime = ZonedDateTime.parse(timestamp)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return zonedDateTime.format(formatter)
    }

    override fun getEndOfCurrentDay(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return today.format(formatter)
    }

    override fun getEndOfCurrentWeek(): String {
        val today = LocalDate.now()
        val endOfWeek = today.with(DayOfWeek.FRIDAY)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return endOfWeek.format(formatter)
    }

    override fun getStartOfCurrentWeek(): String {
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.SATURDAY).minusWeeks(1)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return startOfWeek.format(formatter)
    }

    override fun convertDateTimeToLong(dateTime: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(ZoneId.systemDefault())
        val date = dateFormat.parse(dateTime)
        return date?.time ?: 0L
    }

    override fun formatDateTime(inputDate: String): String {
        // Define the input formatter for ISO 8601 with 'Z' (UTC time)
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        // Parse the input date string as a ZonedDateTime in UTC
        val zonedDateTime = ZonedDateTime.parse(inputDate, inputFormatter.withZone(ZoneId.systemDefault()))

        // Define the output formatter for the desired format
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, hh:mm a", Locale.getDefault())

        // Format the ZonedDateTime to the desired output format in the system default time zone
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(outputFormatter)
    }


    override fun formatHourFromTimestamp(timestamp: String): String {
        // Define the input formatter
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        // Define the output formatter
        val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)

        // Parse the timestamp to LocalDateTime
        val dateTime = LocalDateTime.parse(timestamp, inputFormatter)

        // Format the parsed date-time to the desired output format
        return dateTime.format(outputFormatter)
    }
}