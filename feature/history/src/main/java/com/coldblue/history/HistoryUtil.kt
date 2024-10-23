package com.coldblue.history

import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.MandaTodo
import com.coldblue.model.TodoGraph
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object HistoryUtil {

    /**
     * List<controller>
     * 첫번째 : 요일 리스트(일 ~ 토 7개)
     * 나머자 : Week 리스트(7개)
     */
    fun makeController(year: Int, todoList: List<String>): List<Controller> {
        val resultList = mutableListOf<Controller>()
        val weekList = mutableListOf<ControllerDayState>()
        val startDay = LocalDate.of(year, 1, 1)
        val endDay = LocalDate.of(year, 12, 31)
        val today = LocalDate.now()
        var currentDay = startDay
        val startDayOfWeek = startDay.dayOfWeek.value
        var currentMonth= 0
        val dayOfWeekList = listOf("","월","","수","","금","")

        // 요일 텍스트 박스 삽입
        for(dayOfWeek in dayOfWeekList){
            weekList.add(ControllerDayState.Default(dayOfWeek))
        }
        resultList.add(Controller(month = "", controllerDayList = weekList.toList()))
        weekList.clear()

        // 첫 주에 시작하는 요일 위치를 맞추기 위해 빈값 삽입
        if (startDayOfWeek != 7) {
            repeat(startDayOfWeek) {
                weekList.add(ControllerDayState.Default())
            }
        }
        // 1~12월까지 size가 7인 리스트 추가
        // 하루 기준 Todo가 있는지 없는지 판단
        // 하나라도 달성 시 채워주기
        while (true) {
            if (currentDay > endDay) {
                // 아래서 계산한 12월 마지막 주 리스트 삽입 후 Break
                resultList.add(Controller(month = "", controllerDayList = weekList.toList()))
                break
            }

            // Todo가 존재하는 Day
            if (todoList.contains(currentDay.toString())) {
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
                val monthByFirstDayOfWeek = currentDay.month.value
                val controllerFirstMonth = if(monthByFirstDayOfWeek == 12) 1 else monthByFirstDayOfWeek
                resultList.add(
                    Controller(
                        month = if (currentMonth == controllerFirstMonth) "" else currentDay.month.value.toString(),
                        controllerDayList = weekList.toList()
                    )
                )
                currentMonth = controllerFirstMonth
                weekList.clear()
            }
            currentDay = currentDay.plusDays(1)
        }
        return resultList
    }

    fun calculateRank(graph: List<TodoGraph>, currentIndex: Int): Int? {
        val sortedGraph = sortedGraphList(graph)
        val index = sortedGraph.indexOf(currentIndex)
        if(index in 0..2){
            return index
        }else{
            return null
        }
    }

    /**
     * Graph List를 정렬 기준에 맞춰 정렬해주는 함수.
     * 정렬 기준
     * 1. Name이 존재
     * 2. Done Count 기준
     * 3. All Count 기준
     */
    private fun sortedGraphList(graph: List<TodoGraph>): List<Int>{
        return graph.indices.sortedWith((compareByDescending<Int> { graph[it].name.isNotBlank() }.thenByDescending { graph[it].doneCount }.thenByDescending { graph[it].allCount }))
    }

    fun calculateContinueDate(todo: List<String>): Int {
        var presentDate = LocalDate.now().minusDays(1)
        var result = 0

        for(date in todo.reversed()){
            if(presentDate.toString() != date) break
            presentDate = presentDate.minusDays(1)
            result++
        }
        return result
    }

    fun indexToDarkColor(index: Int): Color {
        return when (index) {
            0 -> HMColor.DarkPastel.Pink
            1 -> HMColor.DarkPastel.Red
            2 -> HMColor.DarkPastel.Orange
            3 -> HMColor.DarkPastel.Yellow
            4 -> HMColor.DarkPastel.Green
            5 -> HMColor.DarkPastel.Blue
            6 -> HMColor.DarkPastel.Mint
            else -> HMColor.DarkPastel.Purple
        }
    }

    fun indexToLightColor(index: Int): Color {
        return when (index) {
            0 -> HMColor.LightPastel.Pink
            1 -> HMColor.LightPastel.Red
            2 -> HMColor.LightPastel.Orange
            3 -> HMColor.LightPastel.Yellow
            4 -> HMColor.LightPastel.Green
            5 -> HMColor.LightPastel.Blue
            6 -> HMColor.LightPastel.Mint
            else -> HMColor.LightPastel.Purple
        }
    }

    fun dateToString(date: String): String{
        val (year, month, day) = date.split("-")
        return "${year}년 ${month}월 ${day}일"
    }

    fun initCurrentIndex(graph: List<TodoGraph>): Int{
        return graph.indexOfFirst { it.name.isNotBlank() }
    }

    fun initCurrentYear(list: List<String>, present: String): String{
        return if(list.contains(present) || list.isEmpty()) present
        else list.first()
    }

    fun initCurrentDate(year: String, date: LocalDate): LocalDate {
        return LocalDate.of(year.toInt(), date.month.value, date.dayOfWeek.value)
    }


    fun emptyGraphList(): List<TodoGraph>{
        return listOf(
            TodoGraph(allCount = 10, doneCount = 6, colorIndex = 1),
            TodoGraph(allCount = 5, doneCount = 2, colorIndex = 2),
            TodoGraph(allCount = 8, doneCount = 8, colorIndex = 3),
            TodoGraph(allCount = 3, doneCount = 1, colorIndex = 4),
            TodoGraph(allCount = 1, doneCount = 1, colorIndex = 5),
            TodoGraph(allCount = 9, doneCount = 2, colorIndex = 6),
            TodoGraph(allCount = 6, doneCount = 6, colorIndex = 7),
            TodoGraph(allCount = 7, doneCount = 4, colorIndex = 8),
        )
    }

    fun emptyTodoList(): List<MandaTodo>{
        return listOf(
            MandaTodo(title = "", isDone = true, mandaIndex = 1, repeatCycle = 0),
            MandaTodo(title = "", isDone = true, mandaIndex = 1, repeatCycle = 0),
        )
    }

    /**
     * History Controller 의 Init Scroll Postion 을 계산해주는 함수.
     * 중앙에 오기위해 임의의 값 6을 빼줌
     */
    fun calculateScrollerIndex(date: LocalDate): Int {
        val baseDate = LocalDate.of(date.year, 1, 1)
        val dayCnt = ChronoUnit.DAYS.between(baseDate, date).toInt()
        var dayOfWeekCnt = dayCnt / 7
        if(dayOfWeekCnt > 6) dayOfWeekCnt-=6
        return dayOfWeekCnt
    }
}