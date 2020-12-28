package org.itmodreamteam.myrest.server.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeFormatter {

    private val dateFormat = DateTimeFormatter.ofPattern("d.MM.yyyy")
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    private val dateTimeFormat = DateTimeFormatter.ofPattern("HH:mm d.MM.yyyy")

    fun format(date: LocalDate): String = date.format(dateFormat)

    fun format(time: LocalTime): String = time.format(timeFormat)

    fun format(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormat)
}
