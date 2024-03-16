package com.coldblue.data.util

import com.coldblue.model.MyTime
import com.orhanobut.logger.Logger
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
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    return if (this.isNullOrBlank()) null
    else LocalTime.parse(this, formatter)

}

fun String.toDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, formatter)
}


fun LocalTime.getDisplayName(): String {
    return if (isAm()) {
        "오전 ${hour.getAm()}:${minute.padTwoZero()}"
    } else {
        "오후 ${hour.getPm()}:${minute.padTwoZero()}"
    }
}

fun LocalTime.getAmPmHour(amPm: String): LocalTime {
    return if (amPm == "오전") {
        withHour(hour.toAm())
    } else {
        withHour(hour.toPm())
    }
}

fun MyTime.getAmPmHour(): LocalTime {
    val ampmHour = if (ampm == "오전") hour.toAm() else hour.toPm()
    return LocalTime.now().withHour(ampmHour).withMinute(minute)
}

fun LocalTime.asMyTime(): MyTime {
    return MyTime(
        ampm = if (isAm()) "오전" else "오후",
        hour = if (isAm()) hour.getAm() else hour.getPm(),
        minute = minute
    )
}

fun Int.getAm(): Int {
    return if (this == 0) 12 else this
}

fun Int.getPm(): Int {
    return if (this == 12) 12 else this - 12
}


fun Int.toAm(): Int {
    return if (this == 12) 0 else this
}

fun Int.toPm(): Int {
    return if (this == 12) 12 else this + 12
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

fun LocalDate.formatToDot(): String {
    return this.toString().replace("-", ".")
}

fun LocalDate.toDayOfWeekString(): String {
    return when (this.dayOfWeek.value) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        4 -> "목요일"
        5 -> "금요일"
        6 -> "토요일"
        else -> "일요일"
    }
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

fun List<String>?.toSoredIntList(): List<Int> {
    return if (this.isNullOrEmpty()) emptyList()
    else this.map { it.toInt() }.sorted()

}