package com.coldblue.survey.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.HMButton
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Survey
import com.coldblue.designsystem.R.drawable.comment

@Composable
fun SurveyListContent(
    surveyList: List<Survey>,
    navigateToSurveyDetail: (id: Int) -> Unit,
    updateSurvey: (survey: Survey) -> Unit,
    writeSurvey: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(surveyList) {
                SurveyItem(it, navigateToSurveyDetail, updateSurvey)
            }
        }
        HMButton(
            text = "기능제안하기",
            clickableState = true,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            writeSurvey()
        }

    }
}

@Composable
fun SurveyItem(
    survey: Survey,
    navigateToSurveyDetail: (id: Int) -> Unit = {},
    updateSurvey: (survey: Survey) -> Unit

) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HMColor.Background)
            .clickable {
                navigateToSurveyDetail(survey.id)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.65f)
        ) {

            SurveyStateChip(survey.state)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = survey.title)
            Text(
                text = "${survey.userType} | ${survey.date}",
                style = HmStyle.text12,
                color = HMColor.SubDarkText
            )

        }
        Row(
            modifier = Modifier.padding(end = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint =  HMColor.Text,
                painter = painterResource(id = comment),
                contentDescription = "댓글"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = survey.commentCount.toString())

            TextButton(
                onClick = { updateSurvey(survey) },
                modifier = Modifier,
            ) {
                Icon(
                    tint = if (survey.isLiked) HMColor.NegativeText else HMColor.Text,
                    imageVector = if (survey.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "좋아요"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = survey.likeCount.toString(), color = HMColor.Text)
            }
        }


    }

    HorizontalDivider(color = HMColor.Box)

}

@Composable
fun SurveyStateChip(state: String) {
    val color = when (state) {
        "완료" -> HMColor.SubLightText
        "진행중" -> HMColor.SurveyGreen
        "개발예정" -> HMColor.SurveyYellow
        else -> {
            HMColor.Text
        }
    }
    Box(
        modifier = Modifier
            .width(50.dp)
            .border(1.dp, color, RoundedCornerShape(12.dp)),
        Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            text = state,
            style = HmStyle.text10,
            color = color
        )

    }

}


@Preview
@Composable
fun SurveyListContentPreview() {
    SurveyListContent(
        surveyList = listOf(
            Survey(1, "홈화면 만다라트 표시", "완료", "2024-06-03", 15, "만다라트 제거", "관리자", false,3),
            Survey(2, "위젯", "진행중", "2024-05-11", 1, "만타탙", "관리자", true,3),
            Survey(3, "다크모드 지원", "개발예정", "2024-06-13", 0, "만ㅋ크크크제거", "관리자", false,3),
        ),
        {},
        {},
        {}
    )
}