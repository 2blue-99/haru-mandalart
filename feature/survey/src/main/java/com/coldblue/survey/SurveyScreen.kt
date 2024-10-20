package com.coldblue.survey

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.data.util.LoginState
import com.coldblue.designsystem.component.HMTopBar
import com.coldblue.model.Survey
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.survey.content.SurveyListContent
import com.orhanobut.logger.Logger

@Composable
fun SurveyScreen(
    navigateToSurveyDetail: (id: Int) -> Unit,
    navigateToSurveyWrite: () -> Unit,
    navigateToBackstack: () -> Unit,
    surveyViewModel: SurveyViewModel = hiltViewModel(),
) {
    val surveyUiState by surveyViewModel.surveyUIState.collectAsStateWithLifecycle()

    val authState by surveyViewModel.authState.collectAsStateWithLifecycle()
    val networkState by surveyViewModel.networkState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        surveyViewModel.getSurveyList()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HMTopBar(title = "기능 제안하기") {
            navigateToBackstack()
        }
        SurveyScreenWithState(
            uiState = surveyUiState,
            navigateToSurveyDetail = navigateToSurveyDetail,
            getSurveyList = surveyViewModel::getSurveyList,
            updateSurvey = {
                if (networkState) {
                    when (authState) {
                        LoginState.AuthenticatedLogin -> (surveyViewModel.updateSurvey(it))

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
            },
            writeSurvey = {
                if (networkState) {
                    when (authState) {
                        LoginState.AuthenticatedLogin -> {
                            Logger.d("글쓰는곳 실행됨")
                            navigateToSurveyWrite()
                        }

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
            },
        )
    }
}

@Composable
fun SurveyScreenWithState(
    uiState: SurveyUiState,
    navigateToSurveyDetail: (id: Int) -> Unit,
    getSurveyList: () -> Unit,
    updateSurvey: (survey: Survey) -> Unit,
    writeSurvey: () -> Unit

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
                updateSurvey,
                writeSurvey
            )
        }
    }


}