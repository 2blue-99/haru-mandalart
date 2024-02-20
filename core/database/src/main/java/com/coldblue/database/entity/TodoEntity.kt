package com.coldblue.database.entity

import androidx.room.Entity

@Entity(tableName = "todo")
data class TodoEntity(
    val id: Int
)
