package com.coldblue.model

import java.time.LocalDate

data class CurrentGroup(
    val todoGroupId: Int,
    val name: String="",
    val isDel: Boolean = false,
    val index: Int,
    val date: LocalDate,
    val originId:Int,
    val originGroupId:Int,
    val id: Int=0,
)

