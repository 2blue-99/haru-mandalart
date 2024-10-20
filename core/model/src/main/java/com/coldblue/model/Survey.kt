package com.coldblue.model

data class Survey(
    val id: Int=0,
    val title: String,
    val state: String,
    val date: String,
    val likeCount: Int=0,
    val content: String,
    val userType:String,
    val isLiked: Boolean,
    val commentCount: Int

)

