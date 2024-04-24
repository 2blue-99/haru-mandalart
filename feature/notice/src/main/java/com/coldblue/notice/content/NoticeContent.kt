package com.coldblue.notice.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.coldblue.model.Notice

@Composable
fun NoticeContent(
    noticeList: List<Notice>,
    getNotice: (id: Int) -> Unit
) {
    LazyColumn() {
        items(noticeList) {
            Text(text = it.title)
            Text(text = it.date)
        }
    }

}