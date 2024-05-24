package com.coldblue.model

data class MyTime2(
    val ampm: String,
    val hour: Int,
    val minute: Int,
    val isEdit: Boolean = false
)