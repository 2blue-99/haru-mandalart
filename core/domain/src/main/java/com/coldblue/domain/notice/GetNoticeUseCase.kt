package com.coldblue.domain.notice

import com.coldblue.data.repository.notice.NoticeRepository
import com.coldblue.model.Notice
import javax.inject.Inject

class GetNoticeUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(id: Int): Notice = noticeRepository.getNotice(id)
}