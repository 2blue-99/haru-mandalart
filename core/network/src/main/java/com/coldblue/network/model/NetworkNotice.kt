package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkNotice(
    val id: Int = 0,
    val title: String,
    val content: String="",
    val date: String,
)
