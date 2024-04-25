package com.coldblue.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.model.Survey
import com.coldblue.survey.content.SurveyDetailContent

@Composable
fun SurveyDetailScreen(
    surveyDetailViewModel: SurveyDetailViewModel = hiltViewModel(),
) {
    val survey by surveyDetailViewModel.survey.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveyDetailScreenWithState(
            survey = survey,
            likeSurvey = surveyDetailViewModel::likeSurvey
        )
    }
}

@Composable
fun SurveyDetailScreenWithState(
    survey: Survey?,
    likeSurvey: (id: Int) -> Unit
) {
    if (survey != null) {
        SurveyDetailContent(
            survey,
            likeSurvey
        )
    }

}