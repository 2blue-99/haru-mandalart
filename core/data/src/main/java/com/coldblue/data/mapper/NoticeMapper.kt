package com.coldblue.data.mapper

import com.coldblue.model.Notice
import com.coldblue.network.model.NetworkNotice

object NoticeMapper {
    fun List<NetworkNotice>.asDomain(): List<Notice> {
        return this.map { it.asDomain() }
    }

    fun NetworkNotice.asDomain(): Notice {
        return Notice(id, title, date, content)
    }
}