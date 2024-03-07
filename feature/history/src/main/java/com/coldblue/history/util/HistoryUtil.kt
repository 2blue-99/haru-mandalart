package com.coldblue.history.util

import android.util.Log
import com.coldblue.history.ControllerDayState
import com.coldblue.history.ControllerTimeState
import com.coldblue.history.ControllerWeek
import java.time.LocalDate

object HistoryUtil {
    fun controllerListMaker(year: Int, existList: List<LocalDate>): List<ControllerWeek> {
        val resultList = mutableListOf<ControllerWeek>()
        val weekList = mutableListOf<ControllerDayState>()
        val startDay = LocalDate.of(year, 1, 1)
        val endDay = LocalDate.of(year, 12, 31)
        val today = LocalDate.now()
        var currentDay = startDay
        val startDayOfWeek = startDay.dayOfWeek.value
        var currentMonth: Int = 0

        // 첫 주에 시작하는 요일 위치 맞춰주기
        if (startDayOfWeek != 1)
            repeat(startDayOfWeek - 1) {
                weekList.add(ControllerDayState.Default)
            }

        while (currentDay <= endDay) {
            // Todo가 존재하는 Day
            if (existList.contains(currentDay)) {
                if (currentDay <= today) {
                    weekList.add(ControllerDayState.Exist(ControllerTimeState.Past(currentDay)))
                } else {
                    weekList.add(ControllerDayState.Exist(ControllerTimeState.Future(currentDay)))
                }
                // Todo가 존재하지않는 Day
            } else {
                if (currentDay <= today) {
                    weekList.add(ControllerDayState.Empty(ControllerTimeState.Past(currentDay)))
                } else {
                    weekList.add(ControllerDayState.Empty(ControllerTimeState.Future(currentDay)))
                }
            }

            if (weekList.size == 7) {
                resultList.add(
                    ControllerWeek(
                        if (currentMonth == currentDay.dayOfMonth) 0 else currentDay.dayOfMonth,
                        weekList
                    )
                )
                currentMonth = currentDay.dayOfMonth
            }
            currentDay = currentDay.plusDays(1)
            Log.e("TAG", "controllerListMaker: $resultList", )
        }
        return resultList
    }
}