package com.coldblue.data.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit


fun LocalTime.getDisplayName(): String {
    return if (this.isAm()) {
        "오전 ${this.hour}:${this.minute.padTwoZero()}"
    } else {
        "오후 ${this.hour - 12}:${this.minute.padTwoZero()}"
    }
}

fun Int.padTwoZero(): String {
    return this.toString().padStart(2, '0')
}

fun Int.padTwoSpace(): String {
    return this.toString().padStart(2, ' ')
}

fun LocalDate.isNotToday(): Boolean {
    return this != LocalDate.now()
}

fun LocalDateTime.toMillis(): Long {
    return this.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneId.systemDefault())
        .toEpochSecond() * 1000
}

fun LocalTime.toPm(): LocalTime {
    return this.withHour(this.hour + 12)
}

fun LocalTime.isAm(): Boolean {
    return this.hour < 12
}

fun String.toFirstLocalDate(): LocalDate {
    return LocalDate.parse("$this-01-01")
}

fun String.toLastLocalDate(): LocalDate {
    return LocalDate.parse("$this-12-31")
}