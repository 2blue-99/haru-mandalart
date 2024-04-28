package com.coldblue.data.repository.notice

import com.coldblue.model.Notice

interface NoticeRepository {
    suspend fun getNoticeList(): List<Notice>
    suspend fun getNotice(id:Int): Notice
}