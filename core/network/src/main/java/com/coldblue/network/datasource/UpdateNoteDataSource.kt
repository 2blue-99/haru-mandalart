package com.coldblue.network.datasource

import com.coldblue.network.model.NetWorkUpdateNote


interface UpdateNoteDataSource {
    suspend fun getUpdateNote(): List<NetWorkUpdateNote>

}