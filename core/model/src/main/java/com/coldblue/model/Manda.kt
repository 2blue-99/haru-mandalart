package com.coldblue.model


data class MandaDetail(
    val name: String,
    val mandaId: Int,
    val isDone: Boolean,
    val id: Int = 0
)

data class MandaKey(
    val name: String,
    val colorIndex: Int = 0,
    val id: Int = 0
)