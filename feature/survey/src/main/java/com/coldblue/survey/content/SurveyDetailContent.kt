package com.coldblue.survey.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.component.TopSpacer
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.designsystem.theme.HmStyle
import com.coldblue.model.Survey

@Composable
fun SurveyDetailContent(
    survey: Survey,
    updateSurvey: (survey: Survey) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HMColor.Background)
            .padding(16.dp)
    ) {
        item {
            SurveyStateChip(survey.state)
            Text(text = survey.title, style = HmStyle.text20)
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "${survey.userType} | ${survey.date}",
                style = HmStyle.text16,
                color = HMColor.SubDarkText
            )
        }
        item {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = survey.content, style = HmStyle.text16
            )
        }
        item {
            HorizontalDivider(color = HMColor.Box)
            TopSpacer()
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                border = AssistChipDefaults.assistChipBorder(
                    enabled = true,
                    borderColor = Color.Transparent
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HMColor.Box,
                    contentColor = HMColor.Text,
                    disabledContainerColor = HMColor.Box
                ),
                onClick = { updateSurvey(survey) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "같이 응원하기")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            tint = if (survey.isLiked) HMColor.NegativeText else HMColor.Text,
                            imageVector = if (survey.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "좋아요"
                        )
                        Text(text = survey.likeCount.toString())
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyDetailContentPreview() {
    SurveyDetailContent(
        survey =
        Survey(2, "다크모드 지원", "진행중", "2024-05-11", 1, "다크모드가 있으면 좋을거 같아요.", "관리자", true),
        {}
    )
}