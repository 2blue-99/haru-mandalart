package com.coldblue.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.model.Survey
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.survey.content.SurveyListContent

@Composable
fun SurveyScreen(
    navigateToSurveyDetail: (id:Int) -> Unit,
    navigateToBackstack: () -> Unit,
    surveyViewModel: SurveyViewModel = hiltViewModel(),
) {
    val surveyUiState by surveyViewModel.surveyUIState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        surveyViewModel.getSurveyList()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveyScreenWithState(
            uiState = surveyUiState,
            navigateToSurveyDetail = navigateToSurveyDetail,
            getSurveyList = surveyViewModel::getSurveyList,
            navigateToBackstack = navigateToBackstack
        )
    }
}

@Composable
fun SurveyScreenWithState(
    uiState: SurveyUiState,
    navigateToSurveyDetail: (id:Int) -> Unit,
    getSurveyList: () -> Unit,
    navigateToBackstack: () -> Unit,
) {
    when (uiState) {
        is SurveyUiState.Loading -> {}
        is SurveyUiState.Error -> {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                IconButton(onClick = { getSurveyList() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "다시 시도")
                }
                Text(text = uiState.msg, color = HMColor.SubDarkText)
            }
        }

        is SurveyUiState.Success -> {
            SurveyListContent(
                uiState.surveyList,
                navigateToSurveyDetail,
                navigateToBackstack
            )
        }
    }


}