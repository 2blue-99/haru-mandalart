package com.coldblue.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.model.Survey
import com.coldblue.survey.content.SurveyListContent

@Composable
fun SurveyScreen(
    navigateToSurveyDetail: (id:Int) -> Unit,
    navigateToBackstack: () -> Unit,
    surveyViewModel: SurveyViewModel = hiltViewModel(),
) {
    val surveyUiState by surveyViewModel.surveyUIState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveyScreenWithState(
            uiState = surveyUiState,
            navigateToSurveyDetail = navigateToSurveyDetail,
            navigateToBackstack = navigateToBackstack
        )
    }
}

@Composable
fun SurveyScreenWithState(
    uiState: SurveyUiState,
    navigateToSurveyDetail: (id:Int) -> Unit,
    navigateToBackstack: () -> Unit
) {
    when (uiState) {
        is SurveyUiState.Loading -> {}
        is SurveyUiState.Error -> {}
        is SurveyUiState.Success -> {
            SurveyListContent(
                uiState.surveyList,
                navigateToSurveyDetail,
                navigateToBackstack
            )
        }
    }


}