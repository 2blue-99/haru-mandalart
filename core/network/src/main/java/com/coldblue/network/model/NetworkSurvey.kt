package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkSurvey(
    val id: Int = 0,
    val state: String,
    val title: String,
    val date: String,
    val content: String,
    val is_admin: Boolean,
    val like_count: Int
)
