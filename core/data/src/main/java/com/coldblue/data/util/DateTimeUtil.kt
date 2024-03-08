package com.coldblue.data.util

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit


fun LocalTime.getDisplayName(): String {
    return if (isAm()) {
        "오전 ${hour.getAm()}:${minute.padTwoZero()}"
    } else {
        "오후 ${hour.getPm()}:${minute.padTwoZero()}"
    }
}

fun LocalTime.getAmPmHour(timeString: String): LocalTime {
    return if (timeString == "오전") {
        withHour(hour.toAm())
    } else {
        withHour(hour.toPm())
    }
}

fun Int.getAm(): Int {
    return if (this == 23) 12 else this
}

fun Int.getPm(): Int {
    return if (this == 11) 12 else this - 12
}


fun Int.toAm(): Int {
    return if (this == 12) 0 else this
}

fun Int.toPm(): Int {
    return if (this == 12) 23 else this + 12
}

fun Int.padTwoZero(): String {
    return toString().padStart(2, '0')
}

fun Int.padTwoSpace(): String {
    return toString().padStart(2, ' ')
}

fun LocalDate.isNotToday(): Boolean {
    return this != LocalDate.now()
}

fun LocalDateTime.toMillis(): Long {
    return truncatedTo(ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
}

fun LocalTime.isAm(): Boolean {
    return when (hour) {
        23, in 0..10 -> true
        else -> false
    }
}

fun Int.toFirstLocalDate(): LocalDate {
    return LocalDate.parse("$this-01-01")
}

fun Int.toLastLocalDate(): LocalDate {
    return LocalDate.parse("$this-12-31")
}