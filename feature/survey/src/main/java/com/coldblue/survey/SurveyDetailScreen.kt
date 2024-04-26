package com.coldblue.survey

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.LoginState
import com.coldblue.model.Survey
import com.coldblue.survey.content.SurveyDetailContent

@Composable
fun SurveyDetailScreen(
    surveyDetailViewModel: SurveyDetailViewModel = hiltViewModel(),
) {
    val survey by surveyDetailViewModel.surveyState.collectAsStateWithLifecycle()
    val authState by surveyDetailViewModel.authState.collectAsStateWithLifecycle()
    val networkState by surveyDetailViewModel.networkState.collectAsStateWithLifecycle()

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveyDetailScreenWithState(
            survey = survey
        ) {
            if (networkState) {
                when (authState) {
                    LoginState.AuthenticatedLogin -> (surveyDetailViewModel.updateSurvey(it))

                    else -> {
                        Toast.makeText(
                            context,
                            "로그인이 필요합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "인터넷 연결을 확인하세요.",
                    Toast.LENGTH_SHORT
                ).show()
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