package com.coldblue.survey.content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coldblue.model.Survey

@Composable
fun SurveyDetailContent(
    survey: Survey,
    likeSurvey: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
        }
    }
}
@Preview
@Composable
fun SurveyDetailContentPreview() {
    SurveyDetailContent(
        survey =
            Survey(2, "위젯", "진행중", "2024-05-11", 1, "만타탙", "관리자", true),
        {}
    )
}