package com.coldblue.data.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getUpdateTime(): String {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
}

fun String?.toTime(): LocalTime? {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return if (this.isNullOrBlank()) null
    else LocalTime.parse(this.take(8), formatter)

}

fun String.toDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, formatter)
}


fun LocalDateTime.isPassed(): Boolean {
    return this.isBefore(LocalDateTime.now())
}

fun LocalDateTime.toMillis(): Long {
    return truncatedTo(ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
}

fun LocalDate.formatToDot(): String {
    return this.toString().replace("-", ".")
}



fun LocalTime.isAm(): Boolean {
    return when (hour) {
        in 0..11 -> true
        else -> false
    }
}

fun Int.toFirstLocalDate(): LocalDate {
    return LocalDate.parse("$this-01-01")
}

fun Int.toLastLocalDate(): LocalDate {
    return LocalDate.parse("$this-12-31")
}

fun List<String>?.toHistoryList(): List<Int> {
    val localYear = LocalDate.now().year
    return if (this == null)
        listOf(localYear)
    else {
        val list: List<Int> = this.map { it.toInt() }
        if (!list.contains(localYear))
            (list + listOf(localYear)).sorted()
        else
            list.sorted()
    }

}

fun LocalDate.isMatch(plusDay: Long): Boolean {
    return this == LocalDate.now().plusDays(plusDay)
}

