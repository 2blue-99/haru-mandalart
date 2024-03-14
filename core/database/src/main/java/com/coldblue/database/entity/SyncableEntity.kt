package com.coldblue.database.entity

interface SyncableEntity {
    val updateTime:String
    val isSync: Boolean
    val originId: Int
    val id: Int
}







