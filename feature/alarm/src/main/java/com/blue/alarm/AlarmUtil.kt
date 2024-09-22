package com.blue.alarm

object AlarmUtil {
    fun Int.dayOfWeekToText(): String {
        return when(this){
            0 -> "월요일"
            1 -> "화요일"
            2 -> "수요일"
            3 -> "목요일"
            4 -> "금요일"
            5 -> "토요일"
            else -> "일요일"
        }
    }
}