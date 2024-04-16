package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetWorkUpdateNote(
    val id: Int = 0,
    val update_time: String,
    val update_content: String,
)

