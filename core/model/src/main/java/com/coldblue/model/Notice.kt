package com.coldblue.model

data class Notice(
    val id: Int,
    val title: String,
    val date: String,
    val content: String = "",
)

