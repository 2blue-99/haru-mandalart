package com.coldblue.data.repository.notice

import com.coldblue.data.mapper.NoticeMapper.asDomain
import com.coldblue.model.Notice
import com.coldblue.network.datasource.NoticeDataSource
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeDataSource: NoticeDataSource
) : NoticeRepository {
    override suspend fun getNoticeList(): List<Notice> {
        return noticeDataSource.getNoticeList().asDomain()
    }

    override suspend fun getNotice(id: Int): Notice {
        return noticeDataSource.getNotice(id).asDomain()
    }

}