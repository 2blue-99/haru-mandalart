package com.coldblue.network.datasource

import com.coldblue.network.model.NetworkNotice

interface NoticeDataSource {
    suspend fun getNoticeList(): List<NetworkNotice>
    suspend fun getNotice(id:Int): NetworkNotice
}