package com.coldblue.notice.content

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Notice

@Composable
fun NoticeContent(
    noticeList: List<Notice>,
    getNotice: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        items(noticeList) {
            NoticeItem(it, getNotice)
        }
    }
}

@Composable
fun NoticeItem(
    notice: Notice,
    getNotice: (id: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .fillMaxWidth()
            .background(HMColor.Background)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                getNotice(notice.id)
                expanded = !expanded
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(0.8f)
        ) {
            Text(text = notice.date, style = HmStyle.text12, color = HMColor.SubDarkText)
            Text(text = notice.title)

        }
        Icon(
            modifier = Modifier.padding(12.dp),
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "기능 제안하기"
        )
    }
    if (expanded) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(HMColor.Box)
                .padding(16.dp)
        ) {
            Text(text = notice.content)
        }
    }
    HorizontalDivider(color = HMColor.Box)

}

@Preview
@Composable
fun NoticePreview() {
    NoticeContent(
        getNotice = {},
        noticeList = listOf(
            Notice(
                1,
                "하루 만다라트 v2.0 업데이트 안내",
                content = "몇 가지 치명적인 버그(현재 수정됨)를 발견했기에 추가 패치로 주요 목표인 버그를 수정하는 데 더해, 이를 기회로 너무 강력한 일부 증강과 가장 과도한 성능을 낸 특성을 조정했습니다.",
                date = "2024-03-30"
            ),
            Notice(
                2,
                "하루 만다라트 v2.0 업데이트 안내",
                content = "몇 가지 치명적인 버그(현재 수정됨)를 발견했기에 추가 패치로 주요 목표인 버그를 수정하는 데 더해, 이를 기회로 너무 강력한 일부 증강과 가장 과도한 성능을 낸 특성을 조정했습니다.",
                date = "2024-03-30"
            ),
            Notice(
                3,
                "하루 만다라트 v2.0 업데이트 안내",
                content = "몇 가지 치명적인 버그(현재 수정됨)를 발견했기에 추가 패치로 주요 목표인 버그를 수정하는 데 더해, 이를 기회로 너무 강력한 일부 증강과 가장 과도한 성능을 낸 특성을 조정했습니다.",
                date = "2024-03-30"
            )
        )
    )
}