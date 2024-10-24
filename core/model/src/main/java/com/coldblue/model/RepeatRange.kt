package com.coldblue.model

import java.time.LocalDate

data class RepeatRange(
    val dateRange: DATE_RANGE,
    val range: Int,
)


enum class DATE_RANGE { DAY, WEEK, MONTH, NONE }

fun repeatCycleToRepeatRange(repeatCycle: Int): RepeatRange {
    val dateRange = repeatCycle.toString()[0].toString().toInt()
    val range = if (repeatCycle > 0) repeatCycle.toString().substring(1).toInt() else 1
    return RepeatRange(
        when (dateRange) {
            1 -> DATE_RANGE.DAY
            2 -> DATE_RANGE.WEEK
            3 -> DATE_RANGE.MONTH
            else -> DATE_RANGE.NONE
        }, range
    )
}

fun repeatCycleToDate(repeatCycle: Int,date:LocalDate): LocalDate {
    val repeatRange = repeatCycleToRepeatRange(repeatCycle)
    when (repeatRange.dateRange) {
        DATE_RANGE.DAY -> return date.plusDays(repeatRange.range.toLong())
        DATE_RANGE.WEEK -> return date.plusWeeks(repeatRange.range.toLong())
        DATE_RANGE.MONTH -> return date.plusMonths(repeatRange.range.toLong())
        DATE_RANGE.NONE -> return date
    }
}


fun repeatRangeToString(repeatRange: RepeatRange): String {
    when (repeatRange.dateRange) {
        DATE_RANGE.DAY -> return "${repeatRange.range}일마다"
        DATE_RANGE.WEEK -> return "${repeatRange.range}주마다"
        DATE_RANGE.MONTH -> return "${repeatRange.range}개월마다"
        DATE_RANGE.NONE -> return ""
    }
}

fun repeatRangeToInt(repeatRange: RepeatRange): Int {
    when (repeatRange.dateRange) {
        DATE_RANGE.DAY -> return "1${repeatRange.range}".toInt()
        DATE_RANGE.WEEK -> return "2${repeatRange.range}".toInt()
        DATE_RANGE.MONTH -> return "3${repeatRange.range}".toInt()
        DATE_RANGE.NONE -> return 0
    }
}

fun dateRangeToString(dateRange: DATE_RANGE): String {
    when (dateRange) {
        DATE_RANGE.DAY -> return "일마다"
        DATE_RANGE.WEEK -> return "주마다"
        DATE_RANGE.MONTH -> return "개월마다"
        DATE_RANGE.NONE -> return "일마다"
    }
}

data class RepeatToggle(
    val isChecked: Boolean,
    val text: String,
    val rangeText: String,
    val dateRange: DATE_RANGE
)
