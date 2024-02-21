package com.coldblue.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoModel(
    val id: Int,
    val update_date_time: String
)
