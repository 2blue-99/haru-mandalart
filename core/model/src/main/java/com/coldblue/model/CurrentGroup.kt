package com.coldblue.model

import java.time.LocalDate

data class CurrentGroup(
    val todoGroupId: Int,
    val name: String="",
    val isSync: Boolean = false,
    val isDel: Boolean = false,
    val updateTime: String = LocalDate.now().toString(),
    val id: Int = 0,
)

data class CurrentGroupWithCnt(
    val currentGroup: CurrentGroup,
    val doneTodoCnt: Int
)

fun CurrentGroup.withCnt(doneTodoCnt: Int): CurrentGroupWithCnt =
    CurrentGroupWithCnt(this, doneTodoCnt = doneTodoCnt)
