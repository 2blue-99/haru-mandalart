package com.coldblue.model


data class MandaDetail(
    val name: String,
    val isDone: Boolean,
    val colorIndex: Int,
    val id: Int = 0
)

data class MandaKey(
    val name: String,
    val colorIndex: Int = 0,
    val id: Int = 0
)

