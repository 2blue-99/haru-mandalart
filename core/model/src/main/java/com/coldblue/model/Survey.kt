package com.coldblue.model

data class Survey(
    val id: Int,
    val title: String,
    val state: String,
    val date: String,
    val likeCount: Int,
    val content: String,
    val userType:String,
    val isLiked: Boolean,
)

