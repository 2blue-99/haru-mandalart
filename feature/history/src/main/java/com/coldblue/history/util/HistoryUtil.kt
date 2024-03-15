package com.coldblue.history.util

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


        // 요일 텍스트 박스 삽입
        weekList.add(ControllerDayState.Default())
        weekList.add(ControllerDayState.Default("월"))
        weekList.add(ControllerDayState.Default())
        weekList.add(ControllerDayState.Default("수"))
        weekList.add(ControllerDayState.Default())
        weekList.add(ControllerDayState.Default("금"))
        weekList.add(ControllerDayState.Default())
        resultList.add(ControllerWeek(month = null, controllerDayList = weekList.toList()))

        weekList.clear()

        // 첫 주에 시작하는 요일 위치 맞춰주기
        if (startDayOfWeek != 1)
            repeat(startDayOfWeek - 1) {
                weekList.add(ControllerDayState.Default())
            }

        while (true) {
            if (currentDay > endDay) {
                // 12월 마지막 주 삽입
                resultList.add(ControllerWeek(month = null, controllerDayList = weekList.toList()))
                break
            }


            // Todo가 존재하는 Day
            if (existList.contains(currentDay)) {
                if (currentDay < today) {
                    weekList.add(ControllerDayState.Exist(ControllerTimeState.Past(currentDay)))
                } else if(currentDay == today){
                    weekList.add(ControllerDayState.Exist(ControllerTimeState.Present(currentDay)))
                }else {
                    weekList.add(ControllerDayState.Exist(ControllerTimeState.Future(currentDay)))
                }
                // Todo가 존재하지않는 Day
            } else {
                if (currentDay < today) {
                    weekList.add(ControllerDayState.Empty(ControllerTimeState.Past(currentDay)))
                } else if(currentDay == today){
                    weekList.add(ControllerDayState.Empty(ControllerTimeState.Present(currentDay)))
                }
                else {
                    weekList.add(ControllerDayState.Empty(ControllerTimeState.Future(currentDay)))
                }
            }

            if (weekList.size == 7) {
                val firstDayMonth = currentDay.minusDays(6).month.value
                resultList.add(
                    ControllerWeek(
                        month = if (currentMonth == firstDayMonth) null else currentDay.month.value,
                        controllerDayList = weekList.toList()
                    )
                )
                currentMonth = firstDayMonth
                weekList.clear()
            }
            currentDay = currentDay.plusDays(1)
        }
        return resultList
    }
}