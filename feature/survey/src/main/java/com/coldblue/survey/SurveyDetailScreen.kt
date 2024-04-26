package com.coldblue.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.HMTextDialog
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.model.Survey
import com.coldblue.survey.content.SurveyDetailContent

@Composable
fun SurveyDetailScreen(
    surveyDetailViewModel: SurveyDetailViewModel = hiltViewModel(),
) {
    val survey by surveyDetailViewModel.surveyState.collectAsStateWithLifecycle()
    val authState by surveyDetailViewModel.authState.collectAsStateWithLifecycle()
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        HMTextDialog(
            targetText = "",
            text = "로그인이 필요합니다.",
            confirmText = "확인",
            tintColor = HMColor.Primary,
            subText = "",
            onDismissRequest = { openDialog = false },
            onConfirmation = {
                openDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveyDetailScreenWithState(
            survey = survey
        ) {
            when (authState) {
                LoginState.AuthenticatedLogin -> (surveyDetailViewModel.updateSurvey(it))

                else -> {
                    openDialog = true
                }
            }
        }
    }
}

@Composable
fun SurveyDetailScreenWithState(
    survey: Survey?,
    updateSurvey: (survey: Survey) -> Unit
) {
    if (survey != null) {
        SurveyDetailContent(
            survey,
            updateSurvey
        )
    }

}