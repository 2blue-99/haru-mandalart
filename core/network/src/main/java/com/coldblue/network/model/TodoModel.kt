package com.coldblue.network.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class TodoModel(
    val id: Int,
    val update_date_time: String
)
