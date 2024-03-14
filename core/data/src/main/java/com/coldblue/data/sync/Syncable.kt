package com.coldblue.data.sync


interface Syncable {
    suspend fun syncRead(): Boolean

    suspend fun syncWrite(): Boolean
    
}