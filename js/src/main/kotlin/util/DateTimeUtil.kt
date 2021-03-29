package util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.js.Date

object DateTimeUtil {

    fun today(): LocalDate {
        val now = Date()
        return LocalDate(now.getFullYear(), now.getMonth() + 1, now.getDate())
    }

    fun formatDate(date: LocalDate): String {
        val month = date.monthNumber.toString().padStart(length = 2, padChar = '0')
        return "${date.dayOfMonth}.$month.${date.year}"
    }

    fun formatHourMinute(date: LocalDateTime): String {
        val hour = date.hour.toString().padStart(length = 2, padChar = '0')
        val minute = date.minute.toString().padStart(length = 2, padChar = '0')
        return "$hour:$minute"
    }
}
